package com.mte.fitnessapp.ui.exercises.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mte.fitnessapp.databinding.RecyclerCategoryLayoutBinding
import com.mte.fitnessapp.model.ExercisesItem
import com.mte.fitnessapp.ui.exercises.listeners.CategoryClickListener

class CategoryAdapter (private val listener : CategoryClickListener) : RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {

    private var categories = emptyList<String>()

    class CategoryVH(private val binding : RecyclerCategoryLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: CategoryClickListener, category : String){
            binding.categoryClickListener = listener
            binding.category = category

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerCategoryLayoutBinding.inflate(layoutInflater,parent,false)
        return CategoryVH(binding)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bind(listener,categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setCategories(list : List<String>){
        categories = list
        notifyDataSetChanged()
    }

}