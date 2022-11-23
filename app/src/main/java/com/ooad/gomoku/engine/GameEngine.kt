package com.ooad.gomoku.engine

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ooad.gomoku.data.Move
import com.ooad.gomoku.data.Player
import com.ooad.gomoku.engine.state.*
import com.ooad.gomoku.observer.ResultPublisher
import com.ooad.gomoku.observer.StatSaver
import com.ooad.gomoku.ui.view.BoardView

enum class StateEnum {
    INIT,
    WHITE_TO_PLAY,
    BLACK_TO_PLAY,
    TERMINATED
}

class GameEngine(player: Player, private val proxy: RemoteGameEngineProxy, boardView: BoardView) : EngineInterface {
    private val publisher = ResultPublisher
    private val board = Board(boardView)
    private val states = mapOf(
        StateEnum.INIT to Init(),
        StateEnum.WHITE_TO_PLAY to WhiteToPlay(this, board),
        StateEnum.BLACK_TO_PLAY to BlackToPlay(this, board),
        StateEnum.TERMINATED to Terminated()
    )
    private var state: State
    private val uiHandler = Handler(Looper.getMainLooper())

    init {
        publisher.registerObserver(StatSaver())
        boardView.piece = player.color
        boardView.engineMoveCallback = ::move
        proxy.engineMoveCallback = ::remoteMove
        state = states[StateEnum.INIT]!!
    }

    fun readyToPlay() {
        changeState(StateEnum.WHITE_TO_PLAY)
    }

    fun changeState(stateEnum: StateEnum) {
        state = states[stateEnum]!!
    }

    override fun move(move: Move) {
        Log.i(TAG, "Trying move: $move")
        if (state.move(move)) {
            Log.i(TAG, "Successful move: $move")
            proxy.move(move)
        }
    }

    private fun remoteMove(move: Move) {
        uiHandler.post {
            Log.i(TAG, "Received remote move: $move")
            if (state.move(move)) {
                Log.i(TAG, "Successful remote move: $move")
            }
        }
    }
}

private const val TAG = "GameEngine"