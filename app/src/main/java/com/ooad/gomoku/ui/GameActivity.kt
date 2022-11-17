package com.ooad.gomoku.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ooad.gomoku.GomokuApp
import com.ooad.gomoku.R
import com.ooad.gomoku.data.Stats
import com.ooad.gomoku.engine.GameEngine
import com.ooad.gomoku.engine.RemoteGameEngineProxy
import com.ooad.gomoku.network.ConnectionManager

class GameActivity : AppCompatActivity() {

    private lateinit var viewModel: GameViewModel
    private lateinit var connManager: ConnectionManager
    private lateinit var gameEngine: GameEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        connManager = (application as GomokuApp).connManager
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.connManager = connManager

        val resultView : TextView = findViewById(R.id.result_display)
        resultView.text = Stats.getDisplayString()

        for (i in 1..10) {
            viewModel.sendData("Hello: $i")
        }
    }

    fun init() {
        // mostly move this to viewModel
        gameEngine = GameEngine()
        val remoteEngine = RemoteGameEngineProxy(connManager)
    }
}