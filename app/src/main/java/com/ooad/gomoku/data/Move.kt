package com.ooad.gomoku.data

import com.ooad.gomoku.engine.Piece

data class Move(val row: Int, val col: Int, val value: Int, val piece: Piece) {
    override fun toString(): String {
        return "move:$row,$col,$value,$piece"
    }

    companion object {
        fun from(move: String): Move {
            val (_, data) = move.split(":")
            val values = data.split(",").map { c -> c.trim() }
            return Move(values[0].toInt(), values[1].toInt(), values[2].toInt(), Piece.valueOf(values[3]))
        }
    }
}