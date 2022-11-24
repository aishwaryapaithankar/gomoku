package com.ooad.gomoku.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ooad.gomoku.GomokuApp
import com.ooad.gomoku.R
import com.ooad.gomoku.data.Player
import com.ooad.gomoku.data.Stats
import com.ooad.gomoku.engine.BoardState
import com.ooad.gomoku.engine.GameEngine
import com.ooad.gomoku.engine.Piece
import com.ooad.gomoku.engine.RemoteGameEngineProxy
import com.ooad.gomoku.network.ConnectionManager
import com.ooad.gomoku.ui.view.BoardView

class GameActivity : AppCompatActivity() {

    private lateinit var viewModel: GameViewModel
    private lateinit var connManager: ConnectionManager
    private lateinit var gameEngine: GameEngine
    private lateinit var boardView: BoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        connManager = (application as GomokuApp).connManager
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.connManager = connManager

        val resultView : TextView = findViewById(R.id.result_display)
        resultView.text = Stats.getDisplayString()
        boardView = findViewById(R.id.board)

        init()
        gameEngine.onGameTerminated = ::onGameTerminated
        gameEngine.readyToPlay()
    }

    private fun init() {
        // mostly move this to viewModel
        val playerName = intent.getStringExtra(KEY_PLAYER_NAME) ?: "You"
        val playerType = intent.getStringExtra(KEY_PLAYER_TYPE) ?: return // Kill game if this happens
        gameEngine = GameEngine(
            Player(playerName, if (playerType == "Host") Piece.WHITE else Piece.BLACK),
            RemoteGameEngineProxy(connManager),
            boardView
        )
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