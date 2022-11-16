package com.ooad.gomoku.data

import android.content.Context
import java.io.File
import java.lang.Integer.parseInt

enum class GameResult {
    WON,
    LOSE,
    DRAW
}

object Stats {
    private lateinit var file: File
    private var won: Int = 0
    private var lost: Int = 0
    private var total: Int = 0
    private var drawn: Int = 0

    fun init(context: Context) {
        createFileIfNotExists(context)
        val content: List<String> = file.readText().split(",").map { content -> content.trim() }

        won = parseInt(content[0])
        lost = parseInt(content[1])
        total = parseInt(content[2])
        drawn = parseInt(content[3])
    }

    fun createFileIfNotExists(context: Context) {
        file = File(context.filesDir, "stats.csv");
        val notExists: Boolean = file.createNewFile()
        if (notExists) {
            file.writeText("0,0,0,0")
        }
    }

    fun update(result: GameResult) {

        when (result) {
            GameResult.WON -> won++
            GameResult.LOSE -> lost++
            GameResult.DRAW -> drawn++
        }
        total++
        file.writeText(getFormattedString())
    }

    private fun getFormattedString(): String = "$won,$lost,$total,$drawn"

    fun getDisplayString(): String = "Won:$won,Lost:$lost,Drawn:$drawn,Total:$total"
}