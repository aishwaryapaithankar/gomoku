package com.ooad.gomoku.engine

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.ui.view.BoardView

enum class Piece {
    NONE,
    BLACK,
    WHITE
}

enum class BoardState {
    IN_PROGRESS,
    WHITE_WON,
    BLACK_WON,
    DRAW
}

class Board(private val boardView: BoardView) {
    private var logicalBoard: Array<Array<Piece>>
    private val boardSize: Int = boardView.boardSize

    var boardState: BoardState = BoardState.IN_PROGRESS

    init {
        logicalBoard = Array(boardSize) { Array(boardSize) { Piece.NONE } }
    }

    private fun isValidMove(move: Move) : Boolean = logicalBoard[move.row][move.col] == Piece.NONE

    fun addPiece(move: Move) : Boolean = if (isValidMove(move)) {
        logicalBoard[move.row][move.col] = move.piece
        boardView.addPiece(move.row, move.col, move.piece)
        boardState = getBoardState(move)
        true
    } else {
        false
    }

    private fun getStateFromPiece(piece: Piece) : BoardState = if (piece == Piece.WHITE) {
        BoardState.WHITE_WON
    } else {
        BoardState.BLACK_WON
    }

    private fun inBounds(x: Int, y: Int): Boolean = x in 0 until boardSize && y in 0 until boardSize

    private fun getBoardState(move: Move): BoardState {
        val (x, y, piece) = move

        //Check if 5 pieces of the same type are found diagonally from the top-left to bottom-right.
        for (i in -4..0) {
            val r = x + i
            val c = y + i
            var found = true
            for (j in 0..4) {
                if (!inBounds(r+j, c+j) || logicalBoard[r+j][c+j] != piece) {
                    found = false
                    break
                }
            }
            if (found) return getStateFromPiece(piece)
        }

        //Check if 5 pieces of the same type are found diagonally from the bottom-left to top-right.
        for (i in 4 downTo 0) {
            val r = x + i
            val c = y - i
            var found = true
            for (j in 0..4) {
                if (!inBounds(r-j, c+j) || logicalBoard[r-j][c+j] != piece) {
                    found = false
                    break
                }
            }
            if (found) return getStateFromPiece(piece)
        }

        //Check if 5 pieces of the same type are found diagonally from top to bottom.
        for (i in -4..0) {
            val r = x + i
            var found = true
            for (j in 0..4) {
                if (!inBounds(r+j, y) || logicalBoard[r+j][y] != piece) {
                    found = false
                    break
                }
            }
            if (found) return getStateFromPiece(piece)
        }

        //Check if 5 pieces of the same type are found diagonally from left to right.
        for (i in -4..0) {
            val c = y + i
            var found = true
            for (j in 0..4) {
                if (!inBounds(x, c+j) || logicalBoard[x][c+j] != piece) {
                    found = false
                    break
                }
            }
            if (found) return getStateFromPiece(piece)
        }

        //Check if the board is full, if not then the game is still in progress.
        for (row in logicalBoard) {
            if (row.contains(Piece.NONE))
                return BoardState.IN_PROGRESS
        }
        //if board is full then the game is drawn.
        return BoardState.DRAW
    }
}