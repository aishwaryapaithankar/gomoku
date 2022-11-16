package com.ooad.gomoku

import android.app.Application
import com.ooad.gomoku.data.GameResult
import com.ooad.gomoku.data.Stats
import com.ooad.gomoku.network.ConnectionManager

class GomokuApp : Application() {

    lateinit var connManager: ConnectionManager

    override fun onCreate() {
        super.onCreate()
        Stats.init(this)
        Stats.update(GameResult.WON)
    }
}