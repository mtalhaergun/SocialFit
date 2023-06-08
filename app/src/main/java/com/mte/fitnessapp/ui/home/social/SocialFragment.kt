package com.mte.fitnessapp.ui.home.social

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mte.fitnessapp.R
import com.mte.fitnessapp.adapter.PostAdapter
import com.mte.fitnessapp.databinding.FragmentProfileBinding
import com.mte.fitnessapp.databinding.FragmentSocialBinding
import com.mte.fitnessapp.model.post.Post
import okhttp3.internal.wait
import java.util.UUID

class SocialFragment : Fragment() {
    private var _binding : FragmentSocialBinding? = null
    private val binding get() = _binding!!

    val storageRef = Firebase.storage.reference
    val db= Firebase.firestore
    lateinit var imageUri: Uri
    private lateinit var auth: FirebaseAuth

    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null

    var list=ArrayList<Post>()

    private lateinit var recyclerViewAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentSocialBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        list.clear()


        binding.fab.setOnClickListener {

            findNavController().navigate(R.id.action_socialFragment_to_uploadPhotoFragment)
        }

        Log.e("ccc","aaa")

        db.collection("photos").orderBy("uploadDate",com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { value2, error1 ->

                if (value2 != null) {
                    value2.documents.forEach {
                        list.add(Post("${it.id}","${it.getField<String>("userName")}","${it.getField<String>("imageUrl")}","${it.getField<String>("caption")}","${it.getField<String>("id")}"))
                        recyclerViewAdapter.notifyDataSetChanged()

                    }
                }
            }

        binding.rVPost.layoutManager= LinearLayoutManager(requireContext())
        binding.rVPost.setHasFixedSize(true)
        recyclerViewAdapter= PostAdapter(list,requireContext())
        binding.rVPost.adapter=recyclerViewAdapter

    }

}