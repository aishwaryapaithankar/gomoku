package com.ooad.gomoku.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ooad.gomoku.GomokuApp
import com.ooad.gomoku.R
import com.ooad.gomoku.network.ConnectionManager

class HostActivity : AppCompatActivity() {

    private lateinit var connManager: ConnectionManager
    private var serverName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            title = "Host Game"
        }

        connManager = ConnectionManager(this)
        (application as GomokuApp).connManager = connManager

        val createButton = findViewById<Button>(R.id.create_game_button)
        createButton.setOnClickListener {
            val nameEditText = findViewById<EditText>(R.id.input_name).apply {
                isEnabled = false
                serverName = text.toString()
            }
            if (serverName.isNullOrEmpty()) {
                nameEditText.isEnabled = true
                return@setOnClickListener
            }

            findViewById<TextView>(R.id.wait_label).isVisible = true
            findViewById<ProgressBar>(R.id.progress_bar).isVisible = true
            findViewById<TextView>(R.id.name_label).text = ""
            createButton.isVisible = false

            connManager.initServer(serverName!!, ::onConnected)
        }
    }

    override fun onResume() {
        super.onResume()
        serverName?.apply { connManager.initServer(this, ::onConnected) }
    }

    override fun onPause() {
        connManager.tearDownService()
        super.onPause()
    }

    override fun onDestroy() {
        connManager.tearDown()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun onConnected() {
        startActivity(Intent(this, GameActivity::class.java).apply {
            putExtra(KEY_PLAYER_NAME, serverName)
            putExtra(KEY_PLAYER_TYPE, "Host")
        })
        finish()
    }
}