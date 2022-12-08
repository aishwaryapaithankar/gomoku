package com.ooad.gomoku.data


//class to store Statistics of remote player
open class Stats(var won: Int, var lost: Int, var drawn: Int, var total: Int) {
    constructor() : this(0, 0, 0, 0)

    override fun toString(): String = "$won,$lost,$drawn,$total"

    companion object {
        // convert string to Stats object
        fun from(stats: String): Stats {
            val (won, lost, drawn, total) = stats.split(",")
                .map { content -> Integer.parseInt(content.trim()) }
            return Stats(won, lost, drawn, total)
        }
    }
}