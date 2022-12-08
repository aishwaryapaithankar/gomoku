package com.ooad.gomoku.engine.state

import com.ooad.gomoku.data.Move

/*
 * @Pattern (State Pattern)
 *
 * The Init state implements the base State interface and overrides the move method to perform the Init State move.
 * As there should be no movement done in Terminated state, the move method simply returns false.
 */
class Init : State {

    //Both players are not allowed to make any move in Init state, so the move function should return false.
    override fun move(move: Move): Boolean {
        return false
    }
}