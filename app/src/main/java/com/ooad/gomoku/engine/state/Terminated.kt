package com.ooad.gomoku.engine.state

import com.ooad.gomoku.data.Move

/*
 * @Pattern (State Pattern)
 *
 * The Terminated state implements the base State interface and overrides the move method to perform the Terminated State move.
 * As there should be no movement done in Terminated state, the move method simply returns false.
 */
class Terminated : State {

    //Both players are not allowed to make any move in Terminated state, so the move function will return false.
    override fun move(move: Move): Boolean {
        return false
    }
}