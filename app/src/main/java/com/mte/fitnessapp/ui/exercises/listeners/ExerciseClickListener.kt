package com.mte.fitnessapp.ui.exercises.listeners

import com.mte.fitnessapp.model.exercises.ExercisesItem

interface ExerciseClickListener {
    fun onExerciseClick(exercise : ExercisesItem)
}