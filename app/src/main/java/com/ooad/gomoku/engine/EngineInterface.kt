package com.ooad.gomoku.engine

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.data.Player

/*
 * @Pattern (Proxy Pattern)
 *
 * The EngineInterface declares the interface of the GameEngine. The RemoteGameEngineProxy will follow this interface to be able to
 * disguise itself as a GameEngine object.
 */
interface EngineInterface {
    fun setPlayer(player: Player)
    fun move(move: Move)
}