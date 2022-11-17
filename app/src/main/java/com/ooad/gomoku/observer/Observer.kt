package com.ooad.gomoku.observer

import com.ooad.gomoku.data.GameResult

interface Observer {
    fun notify(result: GameResult)
}