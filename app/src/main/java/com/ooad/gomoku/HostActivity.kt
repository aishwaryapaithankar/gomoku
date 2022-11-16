package com.ooad.gomoku

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ooad.gomoku.network.ConnectionManager

class HostActivity : AppCompatActivity() {

    private lateinit var connManager: ConnectionManager
    private var serverName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        connManager  = ConnectionManager(this)

        val createButton = findViewById<Button>(R.id.create_game_button)
        createButton.setOnClickListener{
            findViewById<TextView>(R.id.wait_label).isVisible = true
            findViewById<ProgressBar>(R.id.progress_bar).isVisible = true
            createButton.isEnabled = false
            findViewById<EditText>(R.id.input_name).apply {
                isEnabled = false
                serverName = text.toString()
            }

            connManager.initServer(serverName!!)
        }
    }

    override fun onResume() {
        super.onResume()
        serverName?.apply { connManager.initServer(this) }
    }

    override fun onPause() {
        connManager.tearDownService()
        super.onPause()
    }

    override fun onDestroy() {
        connManager.tearDown()
        super.onDestroy()
    }
}