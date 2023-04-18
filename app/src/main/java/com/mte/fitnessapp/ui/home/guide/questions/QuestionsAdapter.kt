package com.mte.fitnessapp.ui.home.guide.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mte.fitnessapp.databinding.FragmentQuestionsBinding
import com.mte.fitnessapp.databinding.RecyclerQuestionsLayoutBinding
import com.mte.fitnessapp.model.questions.QuestionsItem

class QuestionsAdapter(private val listQuestions : List<QuestionsItem>) : RecyclerView.Adapter<QuestionsAdapter.QuestionsVH>() {

    class QuestionsVH(private val binding : RecyclerQuestionsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(question : QuestionsItem){
            binding.question = question
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerQuestionsLayoutBinding.inflate(layoutInflater,parent,false)
        return QuestionsVH(binding)
    }

    override fun onBindViewHolder(holder: QuestionsVH, position: Int) {
        holder.bind(listQuestions[position])

        holder.itemView.setOnClickListener {
            val navigation = QuestionsFragmentDirections.actionQuestionsFragmentToQuestionsDetailFragment(listQuestions[position])
            Navigation.findNavController(it).navigate(navigation)
        }
    }

    override fun getItemCount(): Int {
        return listQuestions.size
    }
}