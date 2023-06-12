package com.mte.fitnessapp.ui.exercises.favorites

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentFavoritesBinding
import com.mte.fitnessapp.model.exercises.ExercisesItem
import com.mte.fitnessapp.model.favorites.Favorites
import com.mte.fitnessapp.ui.exercises.ExercisesFragmentDirections
import com.mte.fitnessapp.ui.exercises.adapters.CategoryAdapter
import com.mte.fitnessapp.ui.exercises.adapters.ExerciseAdapter
import com.mte.fitnessapp.ui.exercises.favorites.adapters.FavoritesRecyclerAdapter
import com.mte.fitnessapp.ui.exercises.favorites.listeners.FavoriteCategoryClickListener
import com.mte.fitnessapp.ui.exercises.listeners.CategoryClickListener
import com.mte.fitnessapp.ui.exercises.listeners.ExerciseClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding : FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoritesViewModel>()
    private lateinit var adapterCategory : CategoryAdapter
    private lateinit var adapterFavorites : FavoritesRecyclerAdapter
    private var categories = arrayListOf<String>()
    private var exercises = listOf<Favorites>()
    private var tempExercises = listOf<Favorites>()
    private var tempCategory = "All"
    private var firstOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(firstOpen){
            createRv()
            observeEvents()
        }else{
            binding.categoryRv.adapter = adapterCategory
            binding.exerciseRv.adapter = adapterFavorites
        }
        listeners()

        val spacing = resources.getDimensionPixelSize(R.dimen.exercise_spacing)
        binding.exerciseRv.addItemDecoration(GridSpacingItemDecoration(2, spacing))

    }

    fun observeEvents() {

        viewModel.favorites.observe(viewLifecycleOwner, Observer {
            exercises = it
            tempExercises = exercises
            if(tempCategory != "All"){
                var listExercise = arrayListOf<Favorites>()
                for (item in exercises){
                    if(item.category == tempCategory){
                        listExercise.add(item)
                    }
                }
                adapterFavorites.setFavorites(listExercise)
                tempExercises = listExercise
            }else{
                adapterFavorites.setFavorites(exercises)
                tempExercises = exercises
            }
        })

        firstOpen = false
    }

    fun createRv(){
        adapterCategory = CategoryAdapter(object : CategoryClickListener {
            override fun onCategoryClick(category: String) {
                if(category != "All"){
                    var listExercise = arrayListOf<Favorites>()
                    for (item in exercises){
                        if(item.category == category){
                            listExercise.add(item)
                        }
                    }
                    adapterFavorites.setFavorites(listExercise)
                    tempExercises = listExercise
                    tempCategory = category
                }else{
                    adapterFavorites.setFavorites(exercises)
                    tempExercises = exercises
                    tempCategory = category
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

        adapterFavorites = FavoritesRecyclerAdapter(viewModel)
        binding.exerciseRv.adapter = adapterFavorites
        adapterFavorites.getDao(viewModel.getFavoritesDao())
        viewModel.getFavorites()
    }

    fun listeners(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null && newText != ""){
                    binding.categoryRv.visibility = View.GONE
                    val filteredList = ArrayList<Favorites>()
                    for(i in exercises){
                        if(i.name.lowercase(Locale.ROOT).contains(newText)){
                            filteredList.add(i)
                        }
                    }
                    adapterFavorites.setFavorites(filteredList)
                }else{
                    binding.categoryRv.visibility = View.VISIBLE
                    adapterFavorites.setFavorites(tempExercises)
                }
                return true
            }
        })

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            firstOpen  = true
            val fragment = FavoritesFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction.commit()
            binding.swipeRefreshLayout.isRefreshing = false
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