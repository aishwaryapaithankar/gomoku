package com.ooad.gomoku.engine

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.network.ConnectionManager

class RemoteGameEngineProxy(private val connManager: ConnectionManager) : EngineInterface {

    init {
        connManager.moveCallback = { data ->
            val move = Move.from(data)
            // do more, like update local board
        }
    }

    override fun move(move: Move) {
        connManager.sendData(move.toString())
    }
}