package com.mte.fitnessapp.ui.home.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentGuideBinding

class GuideFragment : Fragment() {
    private var _binding : FragmentGuideBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentGuideBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.caloriImage.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_guideFragment_to_nutrientsFragment)
        }
    }

}