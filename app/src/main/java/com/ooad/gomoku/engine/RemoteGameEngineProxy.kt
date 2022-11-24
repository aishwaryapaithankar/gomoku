package com.ooad.gomoku.engine

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.network.ConnectionManager

class RemoteGameEngineProxy(private val connManager: ConnectionManager) : EngineInterface {

    lateinit var engineMoveCallback : (Move) -> Unit

    init {
        connManager.dataCallback = { data ->
            when {
                data.startsWith("move") -> {
                    val move = Move.from(data)
                    engineMoveCallback(move)
                }
            }
        }
    }

    override fun move(move: Move) {
        connManager.sendData(move.toString())
    }
}