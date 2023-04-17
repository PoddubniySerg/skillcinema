package ru.skillbox.cinema

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.skillbox.cinema.databinding.ActivityMainBinding
import ru.skillbox.core.R.string.bottom_navigation_is_hidden_nav_graph_argument
import javax.inject.Inject

class MainActivity @Inject constructor() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(ru.skillbox.core.R.id.app_nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNav
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, _, arguments ->
            val key = resources.getString(bottom_navigation_is_hidden_nav_graph_argument)
            val isHidden = arguments?.getBoolean(key) == true
            if (isHidden) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }
}