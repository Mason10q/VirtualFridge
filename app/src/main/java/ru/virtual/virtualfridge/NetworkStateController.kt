package ru.virtual.virtualfridge

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import ru.virtual.core_network.NetworkUtil

class NetworkStateController(private val context: Context) {

    private val sp = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    private val networkUtil = NetworkUtil(context)

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {


        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            if(sp.getBoolean("connected", false)) return

            makeBackUp()
            sp.edit().putBoolean("connected", true).apply()

            checkIfLoggedIn().run {
                setOnlineState(this)
                sendMessage(this)
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            if(!sp.getBoolean("connected", false)) return

            sp.edit().putBoolean("connected", false).apply()

            setOnlineState(false)
            sendMessage(false)
        }
    }

    private fun sendMessage(isOnline: Boolean) {
        context.sendBroadcast(Intent().apply {
            action = "ru.virtual.action.INTERNET_MODE_CHANGED"
            putExtra("ru.virtual.broadcast.MESSAGE", if(isOnline) "Онлайн режим" else "Оффлайн режим")
            addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        })
    }

    private fun checkIfLoggedIn() = sp.getInt("familyId", -1) > 0

    private fun checkConnection() = networkUtil.isInternetAvailable()

    fun checkIfOnline() = checkIfLoggedIn() && checkConnection()

    fun setOnlineState(state: Boolean) = sp.edit().putBoolean("online", state).apply()

    private fun makeBackUp() {}

    fun subscribeOnInternetConnection() = NetworkUtil(context).subscribeOnInternetConnection(networkCallback)

    fun subscribeOnLoggedIn() {
        sp.registerOnSharedPreferenceChangeListener{ sp, key ->

            if(key != "familyId") return@registerOnSharedPreferenceChangeListener

            val isOnline = checkIfOnline()

            setOnlineState(isOnline)
            sendMessage(isOnline)
        }
    }

}

