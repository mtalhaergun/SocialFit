package com.mte.fitnessapp.ui.exercises.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.RecyclerExerciseLayoutBinding
import com.mte.fitnessapp.model.exercises.ExercisesItem
import com.mte.fitnessapp.model.favorites.Favorites
import com.mte.fitnessapp.room.FavoritesDao
import com.mte.fitnessapp.ui.exercises.listeners.ExerciseClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseAdapter (private val listener : ExerciseClickListener) : RecyclerView.Adapter<ExerciseAdapter.ExerciseVH>() {

    private var exercises = emptyList<ExercisesItem>()
    var fdao : FavoritesDao? = null

    class ExerciseVH(private val binding : RecyclerExerciseLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: ExerciseClickListener, exercise : ExercisesItem,position: Int,fdao : FavoritesDao){
            binding.exercise = exercise
            binding.exerciseClickListener = listener
            val job = CoroutineScope(Dispatchers.Main).launch {
                if (fdao.searchName(exercise.name)) {
                    binding.likeButton.setImageResource(R.drawable.like_filled)
                }else{
                    binding.likeButton.setImageResource(R.drawable.like_unfilled)
                }
            }
            binding.executePendingBindings()
            binding.likeButton.setOnClickListener {
                val job = CoroutineScope(Dispatchers.Main).launch {

                    if(fdao.searchName(exercise.name)){
                        val favorite = Favorites(exercise.id,exercise.category,exercise.description,exercise.name,exercise.videoUrl)
                        fdao.deleteFavorites(favorite)
                        binding.likeButton.setImageResource(R.drawable.like_unfilled)
                    }else{
                        val favorite = Favorites(exercise.id,exercise.category,exercise.description,exercise.name,exercise.videoUrl)
                        fdao.addFavorites(favorite)
                        binding.likeButton.setImageResource(R.drawable.like_filled)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerExerciseLayoutBinding.inflate(layoutInflater,parent,false)
        return ExerciseVH(binding)
    }

    override fun onBindViewHolder(holder: ExerciseVH, position: Int) {
        holder.bind(listener,exercises[position],position,fdao!!)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun setExercises(list : List<ExercisesItem>){
        exercises = list
        notifyDataSetChanged()
    }

    fun getDao(favoritesDao : FavoritesDao){
        fdao = favoritesDao
    }
}