package com.mte.fitnessapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.CommentRowBinding
import com.mte.fitnessapp.model.post.Comment

class CommentAdapter(val myDataList: ArrayList<Comment>) : RecyclerView.Adapter<CommentAdapter.DataVH>() {


    class DataVH(itemView: View): RecyclerView.ViewHolder(itemView){

        private lateinit var binding: CommentRowBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVH {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.comment_row,parent,false)
        return DataVH(itemView)
    }

    override fun getItemCount(): Int {
        return myDataList.size
    }

    override fun onBindViewHolder(holder: DataVH, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.userNameTitle1)
        val textView2= holder.itemView.findViewById<TextView>(R.id.commentTitle1)
        textView.text=myDataList[position].userName
        textView2.text=myDataList[position].comment

    }

}