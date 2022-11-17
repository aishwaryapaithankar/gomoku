package com.ooad.gomoku.ui

import androidx.lifecycle.ViewModel
import com.ooad.gomoku.network.ConnectionManager

class GameViewModel : ViewModel() {

    lateinit var connManager: ConnectionManager

    fun sendData(data: String) {
        connManager.sendData(data)
    }
}