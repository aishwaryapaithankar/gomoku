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
        setContentView(R.layout.activity_host)

        val createButton = findViewById<Button>(R.id.create_game_button)
        createButton.setOnClickListener{
            findViewById<TextView>(R.id.wait_label).isVisible = true;
            findViewById<ProgressBar>(R.id.progress_bar).isVisible = true;
            findViewById<Button>(R.id.create_game_button).isEnabled = false;
            findViewById<EditText>(R.id.input_name).isEnabled = false;
        }
    }
}