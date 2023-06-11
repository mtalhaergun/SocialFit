package com.mte.fitnessapp.ui.exercises

import com.mte.fitnessapp.base.BaseRepository
import com.mte.fitnessapp.model.exercises.ExercisesResult
import com.mte.fitnessapp.network.ApiFactory
import javax.inject.Inject

class ExercisesRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getExercises() = safeApiRequest {
        apiFactory.getExercises()
    }
}