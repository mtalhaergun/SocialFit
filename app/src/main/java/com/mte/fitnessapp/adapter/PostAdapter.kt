package com.mte.fitnessapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.PostRecyclerRowBinding
import com.mte.fitnessapp.model.post.Post
import com.mte.fitnessapp.ui.home.social.SocialFragmentDirections
import com.squareup.picasso.Picasso
import okhttp3.internal.notify


class PostAdapter(val myDataList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.DataVH>() {
    val db= Firebase.firestore
    val auth= FirebaseAuth.getInstance()

    val database= FirebaseDatabase.getInstance()
    val databaseReference=database?.reference!!.child("profile")

    class DataVH(itemView: View): RecyclerView.ViewHolder(itemView){

        private lateinit var binding: PostRecyclerRowBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVH {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.post_recycler_row,parent,false)
        return DataVH(itemView)

    }

    override fun getItemCount(): Int {

        return myDataList.size
    }

    override fun onBindViewHolder(holder: DataVH, position: Int) {
        var temp=position
        var currentuser = auth.currentUser
        var userReference = databaseReference?.child(currentuser?.uid!!)
        var userName = ""

        val textView = holder.itemView.findViewById<TextView>(R.id.publisher_user_name_post)
        val textViewName = holder.itemView.findViewById<TextView>(R.id.publisher)
        val textViewCaption = holder.itemView.findViewById<TextView>(R.id.post_caption)
        val textViewComment = holder.itemView.findViewById<TextView>(R.id.Viewcomments)
        val image= holder.itemView.findViewById<ImageView>(R.id.post_image)
        val commentButton=holder.itemView.findViewById<ImageView>(R.id.post_image_comment_btn)
        textView.text=myDataList[position].userName
        textViewName.text=myDataList[position].userName
        textViewCaption.text=myDataList[position].caption
        Log.e("asdddd",myDataList.size.toString())
        Picasso.get().load(myDataList[position].imageUrl).into(image)

        textViewComment.setOnClickListener {
            val action=SocialFragmentDirections.actionSocialFragmentToCommentFragment(myDataList[position].id)
            holder.itemView.findNavController().navigate(action)

        }
        commentButton.setOnClickListener {


            val builder= AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Comment")
            val text= EditText(holder.itemView.context)
            builder.setView(text)
            builder.setPositiveButton("Publish"){_,_->
                userReference?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        userName = (snapshot.child("username").value.toString())

                        val comment = hashMapOf(
                            "userName" to userName,
                            "comment" to text.text.toString(),
                            "commentDate" to Timestamp.now()
                        )
                        Toast.makeText(holder.itemView.context,text.text, Toast.LENGTH_LONG).show()
                        db.collection("posts").addSnapshotListener { value, error ->

                            val documents=value!!.documents
                            documents.forEach{it2->
                                db.collection("posts").document(it2.id).collection("photos").addSnapshotListener { value2, error ->
                                    val photos=value2!!.documents
                                    photos.forEach{
                                        if (it.id==myDataList[temp].id){
                                            db.collection("posts")
                                                .document(it2.id)
                                                .collection("photos")
                                                .document(myDataList[temp].id).collection("comment")
                                                .add(comment)
                                        }
                                    }
                                }

                            }



                        }



                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })


            }
            builder.setCancelable(true)
            val alert=builder.create()
            alert.show()
        }



    }

}