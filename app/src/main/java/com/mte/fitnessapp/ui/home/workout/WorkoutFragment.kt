package com.mte.fitnessapp.ui.home.workout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mte.fitnessapp.databinding.FragmentWorkoutBinding
import com.mte.fitnessapp.ui.exercises.ExercisesActivity

class WorkoutFragment : Fragment() {
    private var _binding : FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentWorkoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exercisesImage.setOnClickListener {
            val intent = Intent(this@WorkoutFragment.context, ExercisesActivity::class.java)
            startActivity(intent)
        }
    }

}