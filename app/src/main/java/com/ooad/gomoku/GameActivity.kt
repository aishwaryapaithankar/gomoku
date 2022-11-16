package com.ooad.gomoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ooad.gomoku.data.Stats

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        var resultView : TextView = findViewById(R.id.result_display);
        resultView.text = Stats.getDisplayString()
    }
}