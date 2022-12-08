package com.ooad.gomoku.engine.state

import android.util.Log
import com.ooad.gomoku.data.Move
import com.ooad.gomoku.engine.*

/*
 * @Pattern (State Pattern)
 *
 * The BlackToPlay state implements the base State interface and overrides the move method to perform the BlackToPlay State move.
 * In BlackToPlay state, only player with Black stone should be allowed to play.
 */
class BlackToPlay(private val engine: GameEngine, private val board: Board) : State {
    override fun move(move: Move): Boolean {

        //Check if the player is allowed to play
        if (move.piece != Piece.BLACK) {
            Log.i(TAG, "Invalid move: ${move.piece}")
            return false
        }

        //Based on if the adding piece to the board was successful change the board state.
        return if (board.addPiece(move)) {
            if (board.boardState == BoardState.IN_PROGRESS)
                engine.changeState(States.WHITE_TO_PLAY)
            else
                engine.changeState(States.TERMINATED)
            true
        } else {
            false
        }
    }
}

private const val TAG = "BlackToPlay"