package com.ooad.gomoku

import android.app.Application
import com.ooad.gomoku.data.GameResult
import com.ooad.gomoku.data.Stats

class GomokuApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Stats.init(this)
        Stats.update(GameResult.WON)
    }
}