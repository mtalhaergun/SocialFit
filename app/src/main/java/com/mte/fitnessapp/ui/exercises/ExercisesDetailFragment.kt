package com.mte.fitnessapp.ui.exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentExercisesDetailBinding

class ExercisesDetailFragment : Fragment() {

    private var _binding : FragmentExercisesDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<ExercisesDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercisesDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exercise = args.argexercise
        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayerView)
    }

}