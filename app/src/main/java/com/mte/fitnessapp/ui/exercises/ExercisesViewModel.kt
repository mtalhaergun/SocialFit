package com.mte.fitnessapp.ui.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mte.fitnessapp.model.exercises.ExercisesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(private val repository : ExercisesRepository) : ViewModel() {

    val exercisesResponse : MutableLiveData<List<ExercisesItem>> = MutableLiveData()

    fun getExercises() = viewModelScope.launch {
        val result = repository.getExercises()
        exercisesResponse.value = result
    }
}