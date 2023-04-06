package com.mte.fitnessapp.ui.exercises

import com.mte.fitnessapp.model.ExercisesResult
import com.mte.fitnessapp.network.ApiFactory
import javax.inject.Inject

class ExercisesRepository @Inject constructor(private val apiFactory: ApiFactory) {

    suspend fun getExercises() : ExercisesResult {
        return apiFactory.getExercises()
    }
}