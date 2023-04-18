package com.mte.fitnessapp.ui.home.guide.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentQuestionsDetailBinding

class QuestionsDetailFragment : Fragment() {
    private var _binding : FragmentQuestionsDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<QuestionsDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionsDetailBinding.inflate(inflater,container,false)
        binding.question = args.questionsarg
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}