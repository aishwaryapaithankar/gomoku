package com.ooad.gomoku

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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