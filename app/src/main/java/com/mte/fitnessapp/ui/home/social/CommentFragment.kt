package com.mte.fitnessapp.ui.home.social

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mte.fitnessapp.R
import com.mte.fitnessapp.adapter.CommentAdapter
import com.mte.fitnessapp.databinding.FragmentCommentBinding
import com.mte.fitnessapp.model.post.Comment


class CommentFragment : Fragment() {

    private lateinit var binding: FragmentCommentBinding
    val db= Firebase.firestore
    val comment=ArrayList<Comment>()
    private lateinit var recyclerViewAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                                            .document(it.id).collection("comment").orderBy("commentDate",com.google.firebase.firestore.Query.Direction.ASCENDING).addSnapshotListener { value3, error3 ->
                                            if (value3!=null){
                                                value3.documents.forEach{
                                                    comment.add(Comment("${it.getField<String>("comment")}","${it.getField<String>("userName")}"))
                                                    recyclerViewAdapter.notifyDataSetChanged()

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
        recyclerViewAdapter= CommentAdapter(comment)
        binding.rVComment.adapter=recyclerViewAdapter
    }
    
}