package com.mte.fitnessapp.model.questions

import java.io.Serializable

data class QuestionsItem(
    val answer: String,
    val id: Int,
    val imageUrl: String,
    val question: String
) : Serializable {}