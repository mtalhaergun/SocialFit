package com.mte.fitnessapp.ui.home.social

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mte.fitnessapp.adapter.CommentAdapter
import com.mte.fitnessapp.databinding.FragmentCommentBinding
import com.mte.fitnessapp.model.post.Comment
import com.mte.fitnessapp.model.post.Post
import com.squareup.picasso.Picasso


class CommentFragment : Fragment() {
    var control:Boolean=false
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: FragmentCommentBinding
    val db= Firebase.firestore
    val comment=ArrayList<Comment>()
    private lateinit var recyclerViewAdapter: CommentAdapter
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCommentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: CommentFragmentArgs by navArgs()
        val data = args.postId
        Log.e("pppp",data)
        comment.clear()
        var currentuser = auth.currentUser
        databaseReference=database?.reference!!.child("profile")
        var userReference = databaseReference?.child(currentuser?.uid!!)
        var userName=""
        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userName = (snapshot.child("username").value.toString())

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        db.collection("posts").addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (value != null) {
                    val documents = value.documents
                    for (document in documents) {
                        db.collection("posts").document(document.id).collection("photos")
                        .addSnapshotListener { value2, error1 ->
                            if (value2 != null) {
                                value2.documents.forEach {
                                    if (it.id==data){
                                        db.collection("posts").document(document.id).collection("photos")
                                            .document(it.id).collection("comment").orderBy("commentDate",
                                                com.google.firebase.firestore.Query.Direction.ASCENDING).addSnapshotListener {
                                                    value3, error3 ->
                                            if (value3!=null){
                                                value3.documents.forEach{
                                                    val control2="${it.getField<String>("userId")}"
                                                    if(document.id==auth.uid||control2==auth.uid) {
                                                        control = true
                                                    }else{
                                                        control=false
                                                    }

                                                    var currentuser = auth.currentUser
                                                    databaseReference=database?.reference!!.child("profile")
                                                    var userReference = databaseReference?.child(control2)
                                                    var userName=""
                                                    userReference?.addValueEventListener(object : ValueEventListener {
                                                        override fun onDataChange(snapshot: DataSnapshot) {
                                                            userName = (snapshot.child("username").value.toString())
                                                            comment.add(Comment(it.id,
                                                                "${it.getField<String>("userId")}"
                                                                ,document.id
                                                                ,"${it.getField<String>("postId")}",
                                                                "${it.getField<String>("comment")}",
                                                                userName,control))
                                                            recyclerViewAdapter.notifyDataSetChanged()
                                                        }

                                                        override fun onCancelled(error: DatabaseError) {

                                                        }
                                                    })


                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            }

        }
        binding.rVComment.layoutManager= LinearLayoutManager(requireContext())
        binding.rVComment.setHasFixedSize(true)
        recyclerViewAdapter= CommentAdapter(comment,requireContext())
        binding.rVComment.adapter=recyclerViewAdapter

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

    }
    
}