package com.ooad.gomoku.engine.state

import android.util.Log
import com.ooad.gomoku.data.Move
import com.ooad.gomoku.engine.*

/*
 * @Pattern (State Pattern)
 *
 * The WhiteToPlay state implements the base State interface and overrides the move method to perform the WhiteToPlay State move.
 * In WhiteToPlay state, only player with White stone should be allowed to play.
 */
class WhiteToPlay(private val engine: GameEngine, private val board: Board) : State {


    override fun move(move: Move): Boolean {
        //Check if the player is allowed to play
        if (move.piece != Piece.WHITE) {
            Log.i(TAG, "Invalid move: ${move.piece}")
            return false
        }
        //Based on if the adding piece to the board was successful change the board state.
        return if (board.addPiece(move)) {
            if (board.boardState == BoardState.IN_PROGRESS)
                engine.changeState(States.BLACK_TO_PLAY)
            else
                engine.changeState(States.TERMINATED)
            true
        } else {
            false
        }
    }
}

private const val TAG = "WhiteToPlay"