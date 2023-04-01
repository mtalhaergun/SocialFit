package com.mte.fitnessapp.ui.exercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.mte.fitnessapp.ViewPagerExercisesAdapter
import com.mte.fitnessapp.databinding.ActivityExercisesBinding

class ExercisesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityExercisesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val adapter = ViewPagerExercisesAdapter(supportFragmentManager,lifecycle)
        binding.viewPagerExercises.adapter = adapter

        TabLayoutMediator(binding.tabLayout,binding.viewPagerExercises){tab, position ->
            when(position){
                0->{
                    tab.text = "All"
                }
                1->{
                    tab.text = "Biceps"
                }
                2->{
                    tab.text = "Triceps"
                }
                3->{
                    tab.text = "Shoulders"
                }
                4->{
                    tab.text = "Chest"
                }
                5->{
                    tab.text = "Back"
                }
                6->{
                    tab.text = "Abs"
                }
                7->{
                    tab.text = "Legs"
                }
            }
        }.attach()
    }
}