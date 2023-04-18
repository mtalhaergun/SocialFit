package com.mte.fitnessapp.network

import com.mte.fitnessapp.model.exercises.ExercisesResult
import com.mte.fitnessapp.model.nutrients.NutrientsResult
import com.mte.fitnessapp.model.questions.QuestionsResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiFactory {

    @GET("mtalhaergun/SocialFit/master/exercises.json")
    suspend fun getExercises() : ExercisesResult

    @GET("mtalhaergun/SocialFit/master/faq.json")
    suspend fun getQuestions() : QuestionsResult

    @GET("https://api.api-ninjas.com/v1/nutrition")
    suspend fun getNutritions(
        @Header("X-Api-Key") apiKey : String,
        @Query("query") query: String
    ) : NutrientsResult


}