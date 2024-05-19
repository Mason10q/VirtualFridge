package ru.virtual.core_network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import javax.inject.Inject


class NetworkUtil(private val context: Context) {

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    fun subscribeOnInternetConnection(networkCallback: NetworkCallback) {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo?.isConnected ?: false;
    }
}