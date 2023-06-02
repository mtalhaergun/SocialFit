package com.mte.fitnessapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.CommentRowBinding
import com.mte.fitnessapp.model.post.Comment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay

class CommentAdapter(var myDataList: ArrayList<Comment>, val mContext : Context) : RecyclerView.Adapter<CommentAdapter.DataVH>() {
    val db= Firebase.firestore
    val auth= FirebaseAuth.getInstance()
    var control:Boolean=false


    val database= FirebaseDatabase.getInstance()
    val databaseReference=database?.reference!!.child("profile")
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
        var profilPhoto=""
        var currentUser=auth.currentUser
        val textView = holder.itemView.findViewById<TextView>(R.id.userNameTitle1)
        val textView2= holder.itemView.findViewById<TextView>(R.id.commentTitle1)
        val deleteButton= holder.itemView.findViewById<ImageView>(R.id.delete_comment)
        var userReference = databaseReference?.child(myDataList[position].userId)
        val profilImage=holder.itemView.findViewById<ImageView>(R.id.publisher_image_profile)
        textView.text=myDataList[position].userName
        textView2.text=myDataList[position].comment
        deleteButton.isVisible = myDataList[position].control==true
        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                profilPhoto = (snapshot.child("profilPhoto").value.toString())
                if (profilPhoto!="null"){
                    Log.e("id:",profilPhoto)
                    Picasso.get().load(profilPhoto).into(profilImage)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        deleteButton.setOnClickListener {
            val popUpMenu = PopupMenu(mContext,it)
            popUpMenu.inflate(R.menu.photo_menu)

            popUpMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.deleteMenu -> {
                        var comment=ArrayList<Comment>()

                        db.collection("posts").document(myDataList[position].userId)
                            .collection("photos").document(myDataList[position].postId)
                            .collection("comment").document(myDataList[position].commentId).delete()
                            .addOnSuccessListener {
                                db.collection("posts").document(myDataList[position].userId)
                                    .collection("photos").document(myDataList[position].postId)
                                    .collection("comment").orderBy("commentDate",com.google.firebase.firestore.Query.Direction.ASCENDING).addSnapshotListener { value, error ->
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
                        true
                    }
                    else -> false
                }
            }
            popUpMenu.show()


        }
    }




}