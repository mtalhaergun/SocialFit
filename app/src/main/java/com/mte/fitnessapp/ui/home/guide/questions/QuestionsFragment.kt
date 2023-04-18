package com.mte.fitnessapp.ui.home.guide.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
class QuestionsFragment : Fragment() {

    private var _binding : FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<QuestionsViewModel>()
    private var listQuestions = listOf<QuestionsItem>()
    private lateinit var adapterQuestions : QuestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getQuestions()
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

        viewModel.questionsResponse.observe(viewLifecycleOwner, Observer {
            listQuestions = it
            adapterQuestions = QuestionsAdapter(listQuestions)
            binding.questionsRv.adapter = adapterQuestions
        })


    }

}