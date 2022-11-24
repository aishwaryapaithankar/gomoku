package com.ooad.gomoku.observer

import com.ooad.gomoku.data.GameResult
import com.ooad.gomoku.data.MyStats

class StatSaver : Observer {
    override fun notify(result: GameResult) {
        MyStats.update(result)
    }
}