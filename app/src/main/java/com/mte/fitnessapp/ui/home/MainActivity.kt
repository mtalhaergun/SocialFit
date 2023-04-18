package com.mte.fitnessapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var bottomNav: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val navView: BottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.container)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.workoutFragment,
                R.id.guideFragment,
                R.id.socialFragment,
                R.id.utilityFragment,
                R.id.profileFragment
            )
        )
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
    
}
