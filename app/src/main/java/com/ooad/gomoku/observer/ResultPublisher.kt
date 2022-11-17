package com.ooad.gomoku.observer

import com.ooad.gomoku.data.GameResult

object ResultPublisher {
    private lateinit var result: GameResult
    private var observers : MutableList<Observer> = ArrayList<Observer>()

    fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyObservers() {
        observers.forEach { observer ->  observer.notify(result) }
    }

    fun updateResult(gameResult: GameResult) {
        result = gameResult
    }
}