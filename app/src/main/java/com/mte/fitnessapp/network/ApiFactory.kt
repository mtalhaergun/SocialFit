package com.mte.fitnessapp.network

import com.mte.fitnessapp.model.ExercisesItem
import com.mte.fitnessapp.model.ExercisesResult
import retrofit2.http.GET

interface ApiFactory {

    @GET("mtalhaergun/SocialFit/master/exercises.json")
    suspend fun getExercises() : ExercisesResult
}