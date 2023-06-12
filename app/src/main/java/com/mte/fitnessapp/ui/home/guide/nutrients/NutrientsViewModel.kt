package com.mte.fitnessapp.ui.home.guide.nutrients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mte.fitnessapp.model.nutrients.NutrientsItem
import com.mte.fitnessapp.network.ApiFactory
import com.mte.fitnessapp.network.NetworkResult
import com.mte.fitnessapp.utils.Constants.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientsViewModel @Inject constructor(private val repository: NutrientsRepository) : ViewModel() {

    val nutrientsResponse : MutableLiveData<List<NutrientsItem>?> = MutableLiveData()
    val onError : MutableLiveData<String?> = MutableLiveData()
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(true)

    fun getNutrients(query : String) = viewModelScope.launch {
        isLoading.value = true
        val request = repository.getNutrients(query)
        when(request){
            is NetworkResult.Success -> {
                nutrientsResponse.value = request.data
                isLoading.value = false
            }
            is NetworkResult.Error -> {
                onError.value = request.message
                isLoading.value = true
            }
        }
    }

}