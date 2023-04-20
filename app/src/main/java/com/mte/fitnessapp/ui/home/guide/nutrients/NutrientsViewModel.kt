package com.mte.fitnessapp.ui.home.guide.nutrients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mte.fitnessapp.model.nutrients.NutrientsItem
import com.mte.fitnessapp.network.ApiFactory
import com.mte.fitnessapp.utils.Constants.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientsViewModel @Inject constructor(private val apiFactory: ApiFactory) : ViewModel() {

    val nutrientsResponse : MutableLiveData<List<NutrientsItem>> = MutableLiveData()

    fun getNutrients(query : String) = viewModelScope.launch {
        val result = apiFactory.getNutritions(API_KEY,query)
        nutrientsResponse.value = result
    }

}