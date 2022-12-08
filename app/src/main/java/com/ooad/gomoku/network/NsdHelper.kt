package com.ooad.gomoku.network

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdManager.RegistrationListener
import android.net.nsd.NsdServiceInfo
import android.util.Log

class NsdHelper(context: Context) {
    /* @Library :
    ** Code borrowed and adapted from:
    ** https://developer.android.com/training/connect-devices-wirelessly/nsd
    **/
    val nsdManager: NsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
    var localPort: Int = 0 //

    lateinit var serviceName: String
    lateinit var onServerDiscovered: (NsdServiceInfo) -> Unit

    private var registrationListener: RegistrationListener? = null
    private var discoveryListener: NsdManager.DiscoveryListener? = null

    fun registerService(name: String) {
        serviceName = name
        val serviceInfo = NsdServiceInfo().apply {
            serviceName = this@NsdHelper.serviceName // Service Name (Player Name)
            serviceType = SERVICE_TYPE // Service Type: "_<protocol>._<transportlayer>"
            port = localPort  // Use the port of the hosted server socket
        }

        registrationListener = registrationListener ?: createRegistrationListener()
        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
    }

    fun discoverService() {
        discoveryListener = discoveryListener ?: createDiscoveryListener()
        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
    }

    fun tearDown() {
        try {
            nsdManager.apply {
                registrationListener?.apply { unregisterService(this) }
                registrationListener = null
                discoveryListener?.apply { stopServiceDiscovery(this) }
                discoveryListener = null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Handled exception: ${e.message}")
        }
    }

    private fun createRegistrationListener() = object : RegistrationListener {
        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo?, code: Int) {
            Log.e(TAG, "Registration failed! Name: ${serviceInfo?.serviceName}, Code: $code")
        }

        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo?, code: Int) {
            Log.e(TAG, "Unregistration failed! Name: ${serviceInfo?.serviceName}, Code: $code")
        }

        override fun onServiceRegistered(serviceInfo: NsdServiceInfo?) {
            Log.i(TAG, "Service Registered! Name: ${serviceInfo?.serviceName}")
        }

        override fun onServiceUnregistered(serviceInfo: NsdServiceInfo?) {
            Log.i(TAG, "Service Unregistered! Name: ${serviceInfo?.serviceName}")
        }
    }

    private val resolveListener = object : NsdManager.ResolveListener {
        override fun onResolveFailed(serviceInfo: NsdServiceInfo?, code: Int) {
            Log.e(TAG, "Resolve failed: $code")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.e(TAG, "Resolve success: $serviceInfo")
            onServerDiscovered(serviceInfo)
        }
    }

    private fun createDiscoveryListener() = object : NsdManager.DiscoveryListener {
        override fun onStartDiscoveryFailed(serviceType: String?, code: Int) {
            Log.e(TAG, "Discovery failed! Code: $code")
            nsdManager.stopServiceDiscovery(this)
        }

        override fun onStopDiscoveryFailed(serviceType: String?, code: Int) {
            Log.e(TAG, "Stop Discovery failed! Code: $code")
            nsdManager.stopServiceDiscovery(this)
        }

        override fun onDiscoveryStarted(regType: String?) {
            Log.i(TAG, "Service discovery started!")
        }

        override fun onDiscoveryStopped(serviceType: String?) {
            Log.i(TAG, "Service discovery stopped!")
        }

        override fun onServiceFound(serviceInfo: NsdServiceInfo) {
            Log.i(TAG, "Service found! Name: ${serviceInfo.serviceName}")
            when {
                serviceInfo.serviceType != SERVICE_TYPE ->
                    Log.i(TAG, "Unknown service type: ${serviceInfo.serviceType}")
                else ->
                    nsdManager.resolveService(serviceInfo, resolveListener)
            }
        }

        override fun onServiceLost(serviceInfo: NsdServiceInfo) {
            Log.i(TAG, "Service lost! Name: ${serviceInfo.serviceName}")
        }
    }
}

private const val TAG = "NsdHelper"
private const val SERVICE_TYPE = "_gomoku._tcp."