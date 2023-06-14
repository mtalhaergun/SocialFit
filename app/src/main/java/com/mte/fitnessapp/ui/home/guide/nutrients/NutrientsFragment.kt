package com.mte.fitnessapp.ui.home.guide.nutrients

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentNutrientsBinding
import com.mte.fitnessapp.ui.home.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NutrientsFragment : Fragment() {

    private var _binding : FragmentNutrientsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NutrientsViewModel>()
    private lateinit var nutrient : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nutrient = context?.getSharedPreferences("Nutrient", Context.MODE_PRIVATE)!!
        if(nutrient.getString("nutrient",null) == null){
            viewModel.getNutrients("kebab")
        }else{
            viewModel.getNutrients(nutrient.getString("nutrient","")!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNutrientsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val nutrient = binding.searchView.query.toString()
            viewModel.getNutrients(nutrient)
        }

        viewModel.nutrientsResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if(it.size == 1){
                    binding.nutrient = it[0]
                    nutrient.edit().putString("nutrient", it[0].name).apply()
                    val walkCalorie = (it[0].calories * 0.24).toInt()
                    val workoutCalorie = (it[0].calories * 0.19).toInt()
                    val runningCalorie = (it[0].calories * 0.14).toInt()
                    val bicycleCalorie = (it[0].calories * 0.13).toInt()
                    binding.textViewWalking.text = "You have to walk for " + walkCalorie.toString() + " minutes"
                    binding.textViewWorkout.text = "You have to workout for " + workoutCalorie.toString() + " minutes"
                    binding.textViewRunning.text = "You have to run for " + runningCalorie.toString() + " minutes"
                    binding.textViewBicycle.text = "You have to cycle for " + bicycleCalorie.toString() + " minutes"
                }else if (it.size > 1){
                    Toast.makeText(context,"Please only search for a single nutrient name",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"Please search for a valid nutrient name",Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.onError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Error! Please try refresh!", Toast.LENGTH_LONG).show()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            handleViews(it)
        })

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            val fragment = NutrientsFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.commit()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun handleViews(isLoading : Boolean = false){
        binding.groupLayout.isVisible = !isLoading
        binding.progressBar.isVisible = isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}