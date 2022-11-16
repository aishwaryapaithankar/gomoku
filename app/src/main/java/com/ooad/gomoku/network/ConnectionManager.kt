package com.ooad.gomoku.network

import android.content.Context
import android.net.nsd.NsdServiceInfo
import android.util.Log
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class ConnectionManager(context: Context) {
    // Code borrowed and adapted from:
    // https://developer.android.com/training/connect-devices-wirelessly/nsd

    private val nsdHelper: NsdHelper = NsdHelper(context)
    private var serverSocket: ServerSocket? = null
    private var clientSocket: Socket? = null
    private val serversFound: MutableList<NsdServiceInfo> = ArrayList()

    private fun initializeServerSocket() {
        // Use any available port for server socket
        if (serverSocket == null) {
            serverSocket = ServerSocket(0).also { socket ->
                nsdHelper.localPort = socket.localPort
            }
        }
    }

    fun initServer(serverName: String) {
        // 1. Create server socket
        initializeServerSocket()
        // 2. Register a network service using the server socket
        nsdHelper.registerService(serverName)
    }

    private fun initializeClientSocket(host: InetAddress, port: Int) {
        clientSocket = Socket(host, port)
        val writer = clientSocket!!.getOutputStream().bufferedWriter()
        val reader = clientSocket!!.getInputStream().bufferedReader()
    }

    private fun onServerDiscovered(serviceInfo: NsdServiceInfo) {
        serversFound.add(serviceInfo)
        Log.i(TAG, "Servers found: ${serversFound.map { s -> s.serviceName }}")
    }

    private fun initClient() {
        serversFound.clear()
        nsdHelper.onServerDiscovered = ::onServerDiscovered
        nsdHelper.discoverService()
    }

    fun tearDownService() {
        nsdHelper.tearDown()
    }

    fun tearDown() {
        tearDownService()
        serverSocket?.apply { close() }
        clientSocket?.apply { close() }
    }
}

private const val TAG = "ConnectionManager"
