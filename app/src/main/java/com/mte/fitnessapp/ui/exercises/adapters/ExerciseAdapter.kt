package com.mte.fitnessapp.ui.exercises.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mte.fitnessapp.databinding.RecyclerExerciseLayoutBinding
import com.mte.fitnessapp.model.exercises.ExercisesItem
import com.mte.fitnessapp.ui.exercises.listeners.ExerciseClickListener

class ExerciseAdapter (private val listener : ExerciseClickListener) : RecyclerView.Adapter<ExerciseAdapter.ExerciseVH>() {

    private var exercises = emptyList<ExercisesItem>()

    class ExerciseVH(private val binding : RecyclerExerciseLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: ExerciseClickListener, exercise : ExercisesItem){
            binding.exercise = exercise
            binding.exerciseClickListener = listener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerExerciseLayoutBinding.inflate(layoutInflater,parent,false)
        return ExerciseVH(binding)
    }

    override fun onBindViewHolder(holder: ExerciseVH, position: Int) {
        holder.bind(listener,exercises[position])
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun setExercises(list : List<ExercisesItem>){
        exercises = list
        notifyDataSetChanged()
    }
}