package com.mte.fitnessapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
                R.id.profileFragment
            )
        )

        val noBottomNavigationIds =
            listOf(R.id.nutrientsFragment,R.id.questionsFragment, R.id.questionsDetailFragment, R.id.bmiFragment,R.id.bodyFatFragment,
            R.id.calorieFragment,R.id.onerepmaxFragment,R.id.commentFragment,R.id.uploadPhotoFragment,R.id.photoDetailFragment, R.id.settingsFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (noBottomNavigationIds.contains(destination.id)) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }

        navView.setupWithNavController(navController)
    }
    
}
