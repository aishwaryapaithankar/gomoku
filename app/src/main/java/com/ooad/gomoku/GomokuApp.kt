package com.ooad.gomoku

import android.app.Application
import com.ooad.gomoku.data.MyStats
import com.ooad.gomoku.network.ConnectionManager

class GomokuApp : Application() {

    lateinit var connManager: ConnectionManager

    override fun onCreate() {
        super.onCreate()
        MyStats.init(this)
    }
}