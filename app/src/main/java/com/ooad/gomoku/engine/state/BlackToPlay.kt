package com.ooad.gomoku.engine.state

import android.util.Log
import com.ooad.gomoku.data.Move
import com.ooad.gomoku.engine.*

class BlackToPlay(private val engine: GameEngine, private val board: Board) : State {
    override fun move(move: Move): Boolean {
        if (move.piece != Piece.BLACK) {
            Log.i(TAG, "Invalid move: ${move.piece}")
            return false
        }

        return if (board.addPiece(move)) {
            if (board.boardState == BoardState.IN_PROGRESS)
                engine.changeState(StateEnum.WHITE_TO_PLAY)
            else
                engine.changeState(StateEnum.TERMINATED)
            true
        } else {
            false
        }
    }
}

private const val TAG = "BlackToPlay"