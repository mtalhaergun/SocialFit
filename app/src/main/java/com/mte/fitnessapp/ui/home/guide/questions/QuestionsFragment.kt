package com.mte.fitnessapp.ui.home.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentQuestionsBinding
import com.mte.fitnessapp.model.questions.QuestionsItem
import com.mte.fitnessapp.model.questions.QuestionsResult
import com.mte.fitnessapp.network.ApiFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuestionsFragment @Inject constructor(private val apiFactory: ApiFactory) : Fragment() {

    private var _binding : FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    private var listQuestions = listOf<QuestionsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            listQuestions = getQuestions()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    suspend fun getQuestions() : QuestionsResult{
        return apiFactory.getQuestions()
    }

}