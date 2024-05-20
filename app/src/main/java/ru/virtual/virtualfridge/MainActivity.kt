package ru.virtual.virtualfridge

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.virtual.virtualfridge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private val networkStateController by lazy { NetworkStateController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        navController = navHostFragment.navController

        with(binding) {
            mainBottomNavigation.itemIconTintList = null
            navController?.let { mainBottomNavigation.setupWithNavController(it) }
        }

        networkStateController.subscribeOnInternetConnection()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        host.navController.popBackStack()
    }
}