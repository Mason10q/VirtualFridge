package ru.virtual.virtualfridge

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import ru.virtual.core_network.NetworkUtil

class NetworkStateController(private val context: Context) {

    private val sp = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            makeBackUp()
            sp.edit().putBoolean("online", checkIfLoggedIn()).apply()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            sp.edit().putBoolean("online", false).apply()
        }
    }

    private fun checkIfLoggedIn() = sp.getInt("familyId", -1) > 0

    private fun makeBackUp() {

    }

    fun subscribeOnInternetConnection() = NetworkUtil(context).subscribeOnInternetConnection(networkCallback)

}

