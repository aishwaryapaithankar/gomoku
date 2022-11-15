package com.ooad.gomoku

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class HostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_game)

        val createButton = findViewById<Button>(R.id.createGameButton)
        createButton.setOnClickListener{
            findViewById<TextView>(R.id.waitLabel).isVisible = true;
            findViewById<ProgressBar>(R.id.progressBar).isVisible = true;
            findViewById<Button>(R.id.createGameButton).isEnabled = false;
            findViewById<EditText>(R.id.inputName).isEnabled = false;
        }
    }
}