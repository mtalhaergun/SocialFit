package com.mte.fitnessapp.ui.exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentExercisesBinding
import com.mte.fitnessapp.model.ExercisesItem
import com.mte.fitnessapp.ui.exercises.adapters.CategoryAdapter
import com.mte.fitnessapp.ui.exercises.adapters.ExerciseAdapter
import com.mte.fitnessapp.ui.exercises.listeners.CategoryClickListener
import com.mte.fitnessapp.ui.exercises.listeners.ExerciseClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercisesFragment : Fragment() {
    private var _binding : FragmentExercisesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ExercisesViewModel>()
    private lateinit var adapterCategory : CategoryAdapter
    private lateinit var adapterExercise : ExerciseAdapter
    private var categories = arrayListOf<String>()
    private var exercises = listOf<ExercisesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getExercises()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercisesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRv()
        observeEvents()
    }

    fun observeEvents() {
        viewModel.exercisesResponse.observe(viewLifecycleOwner, Observer {
            exercises = it
        })
    }

    fun createRv(){
        adapterCategory = CategoryAdapter(object : CategoryClickListener{
            override fun onCategoryClick(category: String) {
                var listExercise = arrayListOf<ExercisesItem>()
                for (item in exercises){
                    if(item.category == category){
                        listExercise.add(item)
                    }
                }
                adapterExercise.setExercises(listExercise)
            }
        })
        categories.add("Biceps")
        categories.add("Tripceps")
        categories.add("Chest")
        categories.add("Back")
        categories.add("Shoulders")
        categories.add("Abs")
        categories.add("Legs")
        adapterCategory.setCategories(categories)
        binding.categoryRv.adapter = adapterCategory

        adapterExercise = ExerciseAdapter(object : ExerciseClickListener{
            override fun onExerciseClick(exercise: ExercisesItem) {

            }
        })
        binding.exerciseRv.adapter = adapterExercise

    }

}