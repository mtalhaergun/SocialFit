package com.mte.fitnessapp.ui.exercises.listeners

import com.mte.fitnessapp.model.ExercisesItem

interface ExerciseClickListener {
    fun onExerciseClick(exercise : ExercisesItem)
}