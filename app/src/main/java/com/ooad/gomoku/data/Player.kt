package com.ooad.gomoku.data

import com.ooad.gomoku.engine.Piece

class Player(val name: String, val color: Piece, val stats: Stats = MyStats) {
    override fun toString(): String = "player:$name,$stats"

    companion object {
        fun from(player: String) : Player {
            val (_, data) = player.split(":")
            val (name, stats) = data.split(",", limit = 2)
            return Player(name, Piece.NONE, Stats.from(stats))
        }
    }
}