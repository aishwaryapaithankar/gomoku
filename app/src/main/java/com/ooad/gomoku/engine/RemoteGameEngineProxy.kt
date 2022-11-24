package com.ooad.gomoku.engine

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.data.Player
import com.ooad.gomoku.network.ConnectionManager

class RemoteGameEngineProxy(private val connManager: ConnectionManager) : EngineInterface {

    lateinit var onMove : (Move) -> Unit
    lateinit var onPlayer : (Player) -> Unit

    init {
        connManager.onData = { data ->
            when {
                data.startsWith("move") -> onMove(Move.from(data))
                data.startsWith("player") -> onPlayer(Player.from(data))
            }
        }
    }

    override fun move(move: Move) {
        connManager.sendData(move.toString())
    }

    override fun setPlayer(player: Player) {
        connManager.sendData(player.toString())
    }
}