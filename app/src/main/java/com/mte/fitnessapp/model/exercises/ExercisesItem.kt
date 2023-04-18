package com.mte.fitnessapp.model.exercises

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExercisesItem(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("videoUrl")
    val videoUrl: String
) : Serializable{

}