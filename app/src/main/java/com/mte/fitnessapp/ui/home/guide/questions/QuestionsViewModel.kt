package com.mte.fitnessapp.ui.home.guide.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mte.fitnessapp.model.questions.QuestionsItem
import com.mte.fitnessapp.network.ApiFactory
import com.mte.fitnessapp.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository : QuestionsRepository) : ViewModel() {

    val questionsResponse : MutableLiveData<List<QuestionsItem>?> = MutableLiveData()
    val onError : MutableLiveData<String?> = MutableLiveData()
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(true)

    fun getQuestions() = viewModelScope.launch {
        isLoading.value = true
        val request = repository.getQuestions()
        when(request){
            is NetworkResult.Success -> {
                questionsResponse.value = request.data
                isLoading.value = false
            }
            is NetworkResult.Error -> {
                onError.value = request.message
                isLoading.value = false
            }
        }
    }
}