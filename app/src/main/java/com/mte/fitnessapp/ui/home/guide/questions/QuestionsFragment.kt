package com.mte.fitnessapp.ui.home.guide.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentQuestionsBinding
import com.mte.fitnessapp.model.exercises.ExercisesItem
import com.mte.fitnessapp.model.questions.QuestionsItem
import com.mte.fitnessapp.model.questions.QuestionsResult
import com.mte.fitnessapp.network.ApiFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class QuestionsFragment : Fragment() {

    private var _binding : FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<QuestionsViewModel>()
    private var listQuestions = listOf<QuestionsItem>()
    private lateinit var adapterQuestions : QuestionsAdapter
    private var firstOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        if(firstOpen){
            viewModel.getQuestions()

            if(viewModel.questionsResponse.value != null){
                listQuestions = viewModel.questionsResponse.value!!
                adapterQuestions = QuestionsAdapter(listQuestions)
                binding.questionsRv.adapter = adapterQuestions
            }

            viewModel.questionsResponse.observe(viewLifecycleOwner, Observer {
                listQuestions = it
                adapterQuestions = QuestionsAdapter(listQuestions)
                binding.questionsRv.adapter = adapterQuestions
            })

            firstOpen = false
        }else{
            binding.questionsRv.adapter = adapterQuestions
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null && newText != ""){
                    val filteredList = ArrayList<QuestionsItem>()
                    for(i in listQuestions){
                        if(i.question.lowercase(Locale.ROOT).contains(newText)){
                            filteredList.add(i)
                        }
                    }
                    adapterQuestions.setQuestions(filteredList)
                }else{
                    adapterQuestions.setQuestions(listQuestions)
                }
                return true
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}