package com.ooad.gomoku.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ooad.gomoku.R
//Class to handle JoinActivity which renders twp fragments in it
class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.join_game)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.join_activity_container, JoinGameFragment.newInstance()).commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}