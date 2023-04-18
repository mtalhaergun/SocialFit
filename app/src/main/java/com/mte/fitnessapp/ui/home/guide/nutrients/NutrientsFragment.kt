package com.mte.fitnessapp.ui.home.guide.nutrients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentNutrientsBinding

class NutrientsFragment : Fragment() {

    private var _binding : FragmentNutrientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNutrientsBinding.inflate(inflater,container,false)
        return binding.root
    }

}