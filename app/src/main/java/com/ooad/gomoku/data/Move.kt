package com.ooad.gomoku.data

import com.ooad.gomoku.engine.Piece

//Data Class to hold the move made by the player
data class Move(val row: Int, val col: Int, val piece: Piece) {
    override fun toString(): String = "move:$row,$col,$piece"

    companion object {
        // to convert String to Move class
        fun from(move: String): Move {
            val (_, data) = move.split(":")
            val values = data.split(",").map { c -> c.trim() }
            return Move(values[0].toInt(), values[1].toInt(), Piece.valueOf(values[2]))
        }
    }
}