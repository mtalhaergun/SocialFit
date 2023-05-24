package com.mte.fitnessapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.CommentRowBinding
import com.mte.fitnessapp.model.post.Comment
import kotlinx.coroutines.delay

class CommentAdapter(var myDataList: ArrayList<Comment>) : RecyclerView.Adapter<CommentAdapter.DataVH>() {
    val db= Firebase.firestore
    val auth= FirebaseAuth.getInstance()
    var control:Boolean=false


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
        var currentUser=auth.currentUser
        val textView = holder.itemView.findViewById<TextView>(R.id.userNameTitle1)
        val textView2= holder.itemView.findViewById<TextView>(R.id.commentTitle1)
        val button= holder.itemView.findViewById<Button>(R.id.delete_comment)
        textView.text=myDataList[position].userName
        textView2.text=myDataList[position].comment
        button.isVisible = myDataList[position].control==true

        button.setOnClickListener {
            var comment=ArrayList<Comment>()

            db.collection("posts").document(myDataList[position].userId)
                .collection("photos").document(myDataList[position].postId)
                .collection("comment").document(myDataList[position].commentId).delete()
                .addOnSuccessListener {
                    db.collection("posts").document(myDataList[position].userId)
                        .collection("photos").document(myDataList[position].postId)
                        .collection("comment").addSnapshotListener { value, error ->
                            if(value==null){}
                            value?.documents!!.forEach {
                                comment.add(Comment(it.id,"${it.getField<String>("userId")}","${it.getField<String>("postId")}","${it.getField<String>("comment")}","${it.getField<String>("userName")}",true))
                            }

                            myDataList.clear()
                            notifyDataSetChanged()
                            myDataList.addAll(comment)
                            notifyDataSetChanged()

                        }




                }
                .addOnFailureListener { e ->
                    // Silme işlemi başarısız olduğunda yapılacak işlemler
                    println("Belge silme hatası: $e")
                }

        }
    }




}