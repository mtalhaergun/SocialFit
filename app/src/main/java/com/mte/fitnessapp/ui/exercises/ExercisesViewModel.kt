package com.mte.fitnessapp.ui.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mte.fitnessapp.model.exercises.ExercisesItem
import com.mte.fitnessapp.network.NetworkResult
import com.mte.fitnessapp.room.FavoritesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(private val repository : ExercisesRepository, private val fdao : FavoritesDao) : ViewModel() {

    val exercisesResponse : MutableLiveData<List<ExercisesItem>?> = MutableLiveData()
    val onError : MutableLiveData<String?> = MutableLiveData()
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(true)

    fun getExercises() = viewModelScope.launch {
        isLoading.value = true
        val request = repository.getExercises()
        when(request){
            is NetworkResult.Success -> {
                exercisesResponse.value = request.data
                isLoading.value = false
            }
            is NetworkResult.Error -> {
                onError.value = request.message
                isLoading.value = false
            }
        }
    }

    fun getFavoritesDao() : FavoritesDao {
        return fdao
    }
}