package com.ooad.gomoku.engine.state

import com.ooad.gomoku.data.Move

/*
 * @Pattern (State Pattern)
 *
 * The State interface which declares move method that all concrete states should implement.
 */
interface State {
    fun move(move: Move): Boolean
}