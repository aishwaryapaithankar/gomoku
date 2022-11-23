package com.ooad.gomoku.engine.state

import android.util.Log
import com.ooad.gomoku.data.Move
import com.ooad.gomoku.engine.Board
import com.ooad.gomoku.engine.GameEngine
import com.ooad.gomoku.engine.Piece
import com.ooad.gomoku.engine.StateEnum

class WhiteToPlay(private val engine: GameEngine, private val board: Board) : State {
    override fun move(move: Move): Boolean {
        if (move.piece != Piece.WHITE) {
            Log.i(TAG, "Invalid move: ${move.piece}")
            return false
        }

        return if (board.addPiece(move)) {
            engine.changeState(StateEnum.BLACK_TO_PLAY)
            true
        } else {
            false
        }
    }
}

private const val TAG = "WhiteToPlay"