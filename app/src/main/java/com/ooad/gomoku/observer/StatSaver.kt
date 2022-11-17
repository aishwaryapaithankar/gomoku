package com.ooad.gomoku.observer

import com.ooad.gomoku.data.GameResult
import com.ooad.gomoku.data.Stats

class StatSaver : Observer {
    override fun notify(result: GameResult) {
        Stats.update(result)
    }
}