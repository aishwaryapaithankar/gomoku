package com.ooad.gomoku.engine

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ooad.gomoku.data.GameResult
import com.ooad.gomoku.data.Move
import com.ooad.gomoku.data.Player
import com.ooad.gomoku.engine.state.*
import com.ooad.gomoku.observer.Observer
import com.ooad.gomoku.observer.ResultPublisher
import com.ooad.gomoku.observer.StatSaver
import com.ooad.gomoku.ui.view.BoardView

enum class States {
    INIT,
    WHITE_TO_PLAY,
    BLACK_TO_PLAY,
    TERMINATED
}

/*
 * @Pattern (Proxy Pattern)
 *
 * GameEngine is a service of proxy pattern.
 */
class GameEngine(private val proxy: RemoteGameEngineProxy, private val boardView: BoardView) :
    EngineInterface {
    private val publisher = ResultPublisher
    private val board = Board(boardView)
    private val states = mapOf(
        States.INIT to Init(),
        States.WHITE_TO_PLAY to WhiteToPlay(this, board),
        States.BLACK_TO_PLAY to BlackToPlay(this, board),
        States.TERMINATED to Terminated()
    )
    private var state: State = states[States.INIT]!!
    private val uiHandler = Handler(Looper.getMainLooper())

    lateinit var onGameTerminated: (BoardState) -> Unit
    lateinit var onRemotePlayer: (Player) -> Unit

    private lateinit var player: Player
    private val resultObserver: Observer = StatSaver()

    init {
        publisher.registerObserver(resultObserver)
        boardView.onMove = ::move
        proxy.onMove = ::remoteMove
        proxy.onPlayer = ::remotePlayer
    }


    //Function to change state after game initialization is done.
    fun readyToPlay() {
        changeState(States.WHITE_TO_PLAY)
    }

    fun changeState(state: States) {
        this.state = states[state]!!
    }

    override fun move(move: Move) {
        Log.i(TAG, "Trying move: $move")
        //delegate move to state pattern
        if (state.move(move)) {
            Log.i(TAG, "Successful move: $move")
            proxy.move(move)
            checkAndDisplayResult()
        }
    }

    private fun remoteMove(move: Move) {
        uiHandler.post {
            Log.i(TAG, "Received remote move: $move")
            if (state.move(move)) {
                Log.i(TAG, "Successful remote move: $move")
                checkAndDisplayResult()
            }
        }
    }

    override fun setPlayer(player: Player) {
        this.player = player
        boardView.piece = player.color
        proxy.setPlayer(player)
    }

    private fun remotePlayer(player: Player) {
        onRemotePlayer(player)
    }

    private fun checkAndDisplayResult() {
        if (board.boardState != BoardState.IN_PROGRESS) {
            publisher.updateResult(
                when (board.boardState) {
                    BoardState.WHITE_WON -> if (player.color == Piece.WHITE) GameResult.WON else GameResult.LOSE
                    BoardState.BLACK_WON -> if (player.color == Piece.BLACK) GameResult.WON else GameResult.LOSE
                    BoardState.DRAW -> GameResult.DRAW
                    else -> GameResult.DRAW
                }
            )
            publisher.removeObserver(resultObserver)
            onGameTerminated(board.boardState)
        }
    }
}

private const val TAG = "GameEngine"