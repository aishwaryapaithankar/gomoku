package com.ooad.gomoku.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ooad.gomoku.GomokuApp
import com.ooad.gomoku.R
import com.ooad.gomoku.data.Player
import com.ooad.gomoku.engine.BoardState
import com.ooad.gomoku.engine.GameEngine
import com.ooad.gomoku.engine.Piece
import com.ooad.gomoku.engine.RemoteGameEngineProxy
import com.ooad.gomoku.network.ConnectionManager
import com.ooad.gomoku.ui.view.BoardView
import com.ooad.gomoku.ui.view.PlayerInfoComponent

class GameActivity : AppCompatActivity() {

    private lateinit var viewModel: GameViewModel
    private lateinit var connManager: ConnectionManager
    private lateinit var gameEngine: GameEngine
    private lateinit var boardView: BoardView
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        connManager = (application as GomokuApp).connManager
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.connManager = connManager

//        val resultView : TextView = findViewById(R.id.result_display)
//        resultView.text = MyStats.getDisplayString()

        init()
        initViews()
        initGameEngine()
    }

    private fun init() {
        // mostly move this to viewModel
        val playerName = intent.getStringExtra(KEY_PLAYER_NAME) ?: "You"
        val playerType = intent.getStringExtra(KEY_PLAYER_TYPE) ?: return // Kill game if this happens
        player = Player(playerName, if (playerType == "Host") Piece.WHITE else Piece.BLACK)
    }

    private fun initViews() {
        boardView = findViewById(R.id.board)

        val remotePlayerView = findViewById<PlayerInfoComponent>(R.id.remote_player)
        remotePlayerView.playerName = "Other"
        val currentPlayerView = findViewById<PlayerInfoComponent>(R.id.current_player)
        currentPlayerView.playerName = player.name
        currentPlayerView.gamesWon = 34
        currentPlayerView.gamesLost = 11
    }

    private fun initGameEngine() {
        gameEngine = GameEngine(RemoteGameEngineProxy(connManager), boardView)
        gameEngine.onGameTerminated = ::onGameTerminated
        gameEngine.setPlayer(player)
        gameEngine.readyToPlay()
    }

    private fun onGameTerminated(boardState: BoardState) {
        AlertDialog.Builder(this)
            .setMessage(
                when (boardState) {
                    BoardState.WHITE_WON -> "WHITE WON"
                    BoardState.BLACK_WON -> "BLACK WON"
                    else -> "DRAW"
                }
            ).setPositiveButton("OK") { _, _ ->
                // Do nothing
            }.create().show()
    }
}

const val KEY_PLAYER_TYPE = "com.ooad.gomoku.KEY_PLAYER_TYPE"