package com.ooad.gomoku.observer

import com.ooad.gomoku.data.GameResult

/*
 * @Pattern (Observer)
 *
 * ResultPublisher is the Concrete Subject of our Observer Framework.
 * We create a Singleton of this class so that it can be easily accessed from
 * anywhere in the code.
 */
object ResultPublisher {
    private lateinit var result: GameResult
    private var observers: MutableList<Observer> = ArrayList()

    fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyObservers() {
        observers.forEach { observer -> observer.notify(result) }
    }

    fun updateResult(gameResult: GameResult) {
        result = gameResult
        notifyObservers()
    }
}