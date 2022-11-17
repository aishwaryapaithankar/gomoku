package com.ooad.gomoku.engine

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.data.Player
import com.ooad.gomoku.observer.ResultPublisher
import com.ooad.gomoku.observer.StatSaver

class GameEngine(private val player: Player) : EngineInterface {
    private val publisher = ResultPublisher

    init {
        publisher.registerObserver(StatSaver())
    }

    override fun move(move: Move) {
        TODO("Not yet implemented")
    }
}