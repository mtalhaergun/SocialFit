package com.mte.fitnessapp.ui.exercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.mte.fitnessapp.databinding.ActivityExercisesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercisesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityExercisesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
    }
}