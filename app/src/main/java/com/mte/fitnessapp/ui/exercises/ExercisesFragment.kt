package com.mte.fitnessapp.ui.exercises

import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentExercisesBinding
import com.mte.fitnessapp.model.exercises.ExercisesItem
import com.mte.fitnessapp.ui.exercises.adapters.CategoryAdapter
import com.mte.fitnessapp.ui.exercises.adapters.ExerciseAdapter
import com.mte.fitnessapp.ui.exercises.listeners.CategoryClickListener
import com.mte.fitnessapp.ui.exercises.listeners.ExerciseClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ExercisesFragment : Fragment() {
    private var _binding : FragmentExercisesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ExercisesViewModel>()
    private lateinit var adapterCategory : CategoryAdapter
    private lateinit var adapterExercise : ExerciseAdapter
    private var categories = arrayListOf<String>()
    private var exercises = listOf<ExercisesItem>()
    private var tempExercises = listOf<ExercisesItem>()
    private var firstOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        if(firstOpen){
            viewModel.getExercises()
            createRv()
            observeEvents()
        }else{
            binding.categoryRv.adapter = adapterCategory
            binding.exerciseRv.adapter = adapterExercise
        }
        listeners()

        val spacing = resources.getDimensionPixelSize(R.dimen.exercise_spacing)
        binding.exerciseRv.addItemDecoration(GridSpacingItemDecoration(2, spacing))

    }

    fun observeEvents() {

        viewModel.exercisesResponse.observe(viewLifecycleOwner, Observer {
            exercises = it
            tempExercises = exercises
            adapterExercise.setExercises(exercises)
        })

        firstOpen = false
    }

    fun createRv(){
        adapterCategory = CategoryAdapter(object : CategoryClickListener{
            override fun onCategoryClick(category: String) {
                if(category != "All"){
                    var listExercise = arrayListOf<ExercisesItem>()
                    for (item in exercises){
                        if(item.category == category){
                            listExercise.add(item)
                        }
                    }
                    adapterExercise.setExercises(listExercise)
                    tempExercises = listExercise
                }else{
                    adapterExercise.setExercises(exercises)
                    tempExercises = exercises
                }
            }
        })
        categories.add("All")
        categories.add("Biceps")
        categories.add("Triceps")
        categories.add("Chest")
        categories.add("Back")
        categories.add("Shoulders")
        categories.add("Abs")
        categories.add("Legs")
        adapterCategory.setCategories(categories)
        binding.categoryRv.adapter = adapterCategory

        adapterExercise = ExerciseAdapter(object : ExerciseClickListener{
            override fun onExerciseClick(exercise: ExercisesItem) {
                val navigation = ExercisesFragmentDirections.actionExercisesFragmentToExercisesDetailFragment(exercise)
                Navigation.findNavController(requireView()).navigate(navigation)
//                val showPopUp = ExercisesDetailFragment(exercise)
//                showPopUp.show(parentFragmentManager,"showPopUp")
            }
        })
        binding.exerciseRv.adapter = adapterExercise
        adapterExercise.getDao(viewModel.getFavoritesDao())
    }

    fun listeners(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null && newText != ""){
                    binding.categoryRv.visibility = View.GONE
                    val filteredList = ArrayList<ExercisesItem>()
                    for(i in exercises){
                        if(i.name.lowercase(Locale.ROOT).contains(newText)){
                            filteredList.add(i)
                        }
                    }
                    adapterExercise.setExercises(filteredList)
                }else{
                    binding.categoryRv.visibility = View.VISIBLE
                    adapterExercise.setExercises(tempExercises)
                }
                return true
            }
        })

        binding.favoritesButton.setOnClickListener {
            val navigation = ExercisesFragmentDirections.actionExercisesFragmentToFavoritesFragment()
            Navigation.findNavController(it).navigate(navigation)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}