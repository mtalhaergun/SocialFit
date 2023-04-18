package com.mte.fitnessapp.ui.home.guide.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mte.fitnessapp.model.questions.QuestionsItem
import com.mte.fitnessapp.network.ApiFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val apiFactory: ApiFactory) : ViewModel() {

    val questionsResponse : MutableLiveData<List<QuestionsItem>> = MutableLiveData()

    fun getQuestions() = viewModelScope.launch {
        val result = apiFactory.getQuestions()
        questionsResponse.value = result
    }
}