package com.ooad.gomoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ooad.gomoku.network.ConnectionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JoinAvailableGameViewModel : ViewModel() {
    lateinit var connManager: ConnectionManager

    fun connectToServer(serverName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            connManager.connectToServer(serverName)
        }
    }
}