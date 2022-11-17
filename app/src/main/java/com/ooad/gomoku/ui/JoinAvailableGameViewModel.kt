package com.ooad.gomoku.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ooad.gomoku.network.ConnectionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JoinAvailableGameViewModel : ViewModel() {
    lateinit var connManager: ConnectionManager

    fun connectToServer(serverName: String, onConnected: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            connManager.connectToServer(serverName, onConnected)
        }
    }
}