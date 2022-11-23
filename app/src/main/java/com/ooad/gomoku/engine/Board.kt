package com.ooad.gomoku.engine

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.ui.view.BoardView

enum class Piece {
    NONE,
    BLACK,
    WHITE
}

class Board(private val boardView: BoardView) {
    private var logicalBoard: Array<Array<Piece>>
    private val boardSize: Int = boardView.boardSize

    init {
        logicalBoard = Array(boardSize) { Array(boardSize) { Piece.NONE } }
    }

    private fun isValidMove(move: Move) : Boolean = logicalBoard[move.row][move.col] == Piece.NONE

    fun addPiece(move: Move) : Boolean = if (isValidMove(move)) {
        boardView.addPiece(move.row, move.col, move.piece)
        true
    } else {
        false
    }
}