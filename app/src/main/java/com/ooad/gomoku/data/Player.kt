package com.ooad.gomoku.data

import com.ooad.gomoku.engine.Piece

//data class to hold current & remote player information
class Player(val name: String, val color: Piece, val stats: Stats = MyStats) {
    override fun toString(): String = "player:$name,$color,$stats"

    companion object {
        //function to convert String to Player object
        fun from(player: String): Player {
            val (_, data) = player.split(":")
            val (name, color, stats) = data.split(",", limit = 3)
            return Player(name, Piece.valueOf(color), Stats.from(stats))
        }
    }
}