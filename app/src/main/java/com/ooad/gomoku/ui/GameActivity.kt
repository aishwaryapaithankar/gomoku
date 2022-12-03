package com.ooad.gomoku.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    private lateinit var connManager: ConnectionManager
    private lateinit var gameEngine: GameEngine
    private lateinit var boardView: BoardView
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.hide()

        connManager = (application as GomokuApp).connManager

        init()
        initViews()
        initGameEngine()
    }

    private fun init() {
        // mostly move this to viewModel
        val playerName = intent.getStringExtra(KEY_PLAYER_NAME) ?: "You"
        val playerType =
            intent.getStringExtra(KEY_PLAYER_TYPE) ?: return // Kill game if this happens
        player = Player(playerName, if (playerType == "Host") Piece.WHITE else Piece.BLACK)
    }

    private fun initViews() {
        findViewById<Button>(R.id.exit_button).setOnClickListener { finish() }
        boardView = findViewById(R.id.board)

        val currentPlayerView = findViewById<PlayerInfoComponent>(R.id.current_player)
        setPlayerInfo(currentPlayerView, player)
    }

    private fun initGameEngine() {
        gameEngine = GameEngine(RemoteGameEngineProxy(connManager), boardView)
        gameEngine.onRemotePlayer = ::onRemotePlayerInfo
        gameEngine.onGameTerminated = ::onGameTerminated
        gameEngine.setPlayer(player)
        gameEngine.readyToPlay()
    }

    private fun onRemotePlayerInfo(player: Player) {
        val remotePlayerView = findViewById<PlayerInfoComponent>(R.id.remote_player)
        Handler(Looper.getMainLooper()).post {
            setPlayerInfo(remotePlayerView, player)
        }
    }

    private fun setPlayerInfo(component: PlayerInfoComponent, player: Player) {
        component.imageResource = if (player.color == Piece.WHITE) {
            R.drawable.white_stone
        } else {
            R.drawable.black_stone
        }
        component.playerName = player.name
        component.gamesWon = player.stats.won
        component.gamesLost = player.stats.lost
    }

    private fun onGameTerminated(boardState: BoardState) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Game Terminated!")
            .setMessage(
                when (boardState) {
                    BoardState.WHITE_WON -> "WHITE WON"
                    BoardState.BLACK_WON -> "BLACK WON"
                    else -> "DRAW"
                }
            ).setPositiveButton("OK") { _, _ ->
                // Do nothing
            }.create()
        dialog.show()
        dialog.findViewById<TextView>(android.R.id.message)!!.apply {
            textSize = 20f
            setTextColor(ContextCompat.getColor(this@GameActivity, R.color.dark_brown))
            gravity = Gravity.CENTER
        }
    }
}

const val KEY_PLAYER_TYPE = "com.ooad.gomoku.KEY_PLAYER_TYPE"
const val KEY_GAME_STARTED = "com.ooad.gomoku.GAME_STARTED"