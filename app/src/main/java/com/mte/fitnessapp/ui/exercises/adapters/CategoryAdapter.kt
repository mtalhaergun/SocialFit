package com.mte.fitnessapp.ui.exercises.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mte.fitnessapp.databinding.RecyclerCategoryLayoutBinding
import com.mte.fitnessapp.ui.exercises.listeners.CategoryClickListener

class CategoryAdapter (private val listener : CategoryClickListener) : RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {

    private var categories = emptyList<String>()
    var selectedPosition : Int = 0

    class CategoryVH(private val binding : RecyclerCategoryLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: CategoryClickListener, category : String,position: Int, selectedPosition: Int){

            if (selectedPosition == position) {
                binding.categoryCard.strokeColor = Color.parseColor("#E1CB0A")
                binding.categoryCard.setCardBackgroundColor(Color.parseColor("#E1CB0A"))
                binding.categoryName.setTextColor(Color.parseColor("#252525"))
            } else {
                binding.categoryCard.strokeColor = Color.parseColor("#E1CB0A")
                binding.categoryCard.setCardBackgroundColor(Color.parseColor("#252525"))
                binding.categoryName.setTextColor(Color.parseColor("#E1CB0A"))
            }

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
        holder.bind(listener,categories[position],position,selectedPosition)

        holder.itemView.setOnClickListener {
            selectedPosition = if (position == selectedPosition) {
                selectedPosition
            } else {
                position
            }
            notifyDataSetChanged()
            listener.onCategoryClick(categories[position])
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setCategories(list : List<String>){
        categories = list
        notifyDataSetChanged()
    }

}