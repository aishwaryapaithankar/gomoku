package com.ooad.gomoku.observer

import com.ooad.gomoku.data.GameResult

/*
 * @Pattern (Observer)
 *
 * The Observer interface with our custom notify method signature
 */
interface Observer {
    fun notify(result: GameResult)
}