package com.ooad.gomoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ooad.gomoku.ui.main.MainFragment

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.join_activity_container, JoinGameFragment.newInstance()).commitNow()
        }
    }
}