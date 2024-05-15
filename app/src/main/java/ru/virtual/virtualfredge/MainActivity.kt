package ru.virtual.virtualfredge

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.virtual.virtualfredge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        navController = navHostFragment.navController

        with(binding) {
            mainBottomNavigation.itemIconTintList = null
            navController?.let { mainBottomNavigation.setupWithNavController(it) }
        }
    }
}