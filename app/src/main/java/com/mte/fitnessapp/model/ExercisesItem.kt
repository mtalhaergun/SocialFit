package com.mte.fitnessapp.model

data class ExercisesItem(
    val category: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val videoUrl: String
)