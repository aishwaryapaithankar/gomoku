package com.ooad.gomoku.data

import com.ooad.gomoku.engine.Piece

class Player(val name: String, val color: Piece, val stats: Stats = MyStats) {
    override fun toString(): String = "player:$name,$color,$stats"

    companion object {
        fun from(player: String) : Player {
            val (_, data) = player.split(":")
            val (name, color, stats) = data.split(",", limit = 3)
            return Player(name, Piece.valueOf(color), Stats.from(stats))
        }
    }
}