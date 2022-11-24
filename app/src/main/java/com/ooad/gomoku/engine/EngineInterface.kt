package com.ooad.gomoku.engine

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.data.Player

interface EngineInterface {
    fun setPlayer(player: Player)
    fun move(move: Move)
}