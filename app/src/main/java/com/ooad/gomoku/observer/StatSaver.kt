package com.ooad.gomoku.observer

import com.ooad.gomoku.data.GameResult
import com.ooad.gomoku.data.MyStats

/*
 * @Pattern (Observer)
 *
 * The Concrete Observer with our custom notify method signature
 */
class StatSaver : Observer {
    override fun notify(result: GameResult) {
        MyStats.update(result)
    }
}