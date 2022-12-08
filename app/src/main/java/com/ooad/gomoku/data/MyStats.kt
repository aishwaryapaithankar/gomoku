package com.ooad.gomoku.data

import android.content.Context
import java.io.File
import java.lang.Integer.parseInt

enum class GameResult {
    WON,
    LOSE,
    DRAW
}

// @Pattern (Singleton - Eager Instantiation)
object MyStats : Stats() {
    private lateinit var file: File

    fun init(context: Context) {
        createFileIfNotExists(context)
        // read stats from internal storage file
        val content = file.readText().split(",").map { content -> parseInt(content.trim()) }
        won = content[0]
        lost = content[1]
        drawn = content[2]
        total = content[3]
    }

    private fun createFileIfNotExists(context: Context) {
        file = File(context.filesDir, "stats.csv")
        val notExists: Boolean = file.createNewFile()
        if (notExists) {
            //write the default 0 values when the file is created.
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
        //write the entire object back to file
        file.writeText(toString())
    }
}