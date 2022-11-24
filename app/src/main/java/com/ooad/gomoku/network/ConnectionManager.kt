package com.ooad.gomoku.network

import android.content.Context
import android.net.nsd.NsdServiceInfo
import android.util.Log
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class ConnectionManager(context: Context) {
    // Code borrowed and adapted from:
    // https://developer.android.com/training/connect-devices-wirelessly/nsd

    private val nsdHelper: NsdHelper = NsdHelper(context)
    private var serverSocket: ServerSocket? = null
    private var clientSocket: Socket? = null
    private val serverList: MutableList<NsdServiceInfo> = ArrayList()
    private lateinit var writer: PrintWriter
    private lateinit var reader: BufferedReader

    lateinit var serverDiscoveredCallback: (String) -> Unit
    lateinit var dataCallback: (String) -> Unit

    private fun initializeServerSocket(onConnected: () -> Unit) {
        if (serverSocket != null)
            return

        // Use any available port for server socket
        serverSocket = ServerSocket(0).also { socket ->
            nsdHelper.localPort = socket.localPort
            Log.i(TAG, "Server Socket Port: ${socket.localPort}")
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                clientSocket = withContext(Dispatchers.IO) { serverSocket!!.accept() }
                Log.i(TAG, "Received conn from: ${clientSocket!!.inetAddress}:${clientSocket!!.port}")
                handleConnection()
                onConnected()
            } catch (e: Exception) {
                Log.e(TAG, "Handled Exception: ${e.message}")
            }
        }
    }

    fun initServer(serverName: String, onConnected: () -> Unit) {
        // 1. Create server socket
        initializeServerSocket(onConnected)
        // 2. Register a network service using the server socket
        nsdHelper.registerService(serverName)
    }

    private fun handleConnection() {
        initIOStreams()
        CoroutineScope(Dispatchers.IO).launch {
            listenForData()
        }
    }

    private fun initIOStreams() {
        Log.i(TAG, "Creating io streams")
        writer = PrintWriter(clientSocket!!.getOutputStream(), true)
        reader = BufferedReader(InputStreamReader(clientSocket!!.getInputStream()))
        Log.i(TAG, "writer: $writer, reader:$reader")
    }

    private fun processData(data: String) {
        dataCallback(data)
    }

    private fun listenForData() {
        var data: String?
        while (true) {
            try {
                data = reader.readLine()
                if (data.isNullOrEmpty()) {
                    Log.i(TAG, "Received null or empty data")
                    break
                }
                Log.i(TAG, "Received data: ($data)")
                processData(data)
            } catch (e: Exception) {
                Log.e(TAG, "Handled Exception: ${e.message}. Exiting listenForData")
                break
            }
        }
    }

    fun sendData(data: String) = CoroutineScope(Dispatchers.IO).launch {
        Log.i(TAG, "sending data: $data")
        writer.println(data)
    }

    suspend fun connectToServer(serverName: String, onConnected: () -> Unit) {
        val serviceInfo: NsdServiceInfo = serverList.first { s -> s.serviceName == serverName }
        clientSocket = withContext(Dispatchers.IO) { Socket(serviceInfo.host, serviceInfo.port) }
        Log.i(TAG, "Connected ${clientSocket!!.inetAddress}:${clientSocket!!.port}")
        handleConnection()
        onConnected()
    }

    private fun onServerDiscovered(serviceInfo: NsdServiceInfo) {
        serverList.add(serviceInfo)
        Log.i(TAG, "Servers found: ${serverList.map { s -> s.serviceName }}")
        serverDiscoveredCallback(serviceInfo.serviceName)
    }

    fun initClient() {
        serverList.clear()
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
