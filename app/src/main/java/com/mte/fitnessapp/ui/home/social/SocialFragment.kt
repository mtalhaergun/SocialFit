package com.mte.fitnessapp.ui.home.social

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
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
import com.mte.fitnessapp.ui.exercises.ExercisesFragment
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
    var firstOpen = true
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


        binding.fab.setOnClickListener {

            findNavController().navigate(R.id.action_socialFragment_to_uploadPhotoFragment)
        }

        if(firstOpen){
            list.clear()
            db.collection("photos").orderBy("uploadDate",com.google.firebase.firestore.Query.Direction.DESCENDING).get().addOnSuccessListener {

                if (it != null) {
                    it.documents.forEach {
                        val userID=it.getField<String>("id")
                        var currentuser = auth.currentUser
                        databaseReference=database?.reference!!.child("profile")
                        var userReference = databaseReference?.child(userID.toString())
                        var userName=""
                        userReference?.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                userName = (snapshot.child("username").value.toString())
                                list.add(Post("${it.id}",userName,"${it.getField<String>("imageUrl")}","${it.getField<String>("caption")}","${it.getField<String>("id")}"))
                                recyclerViewAdapter.notifyDataSetChanged()
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }
                        })
                    }

                }
            }

            recyclerViewAdapter= PostAdapter(list,requireContext())
            binding.rVPost.adapter=recyclerViewAdapter

            Handler().postDelayed({
                binding.rVPost.visibility = View.VISIBLE
                binding.fab.visibility=View.VISIBLE
                binding.eventsShimmerInclude.root.visibility = View.GONE
                binding.eventsShimmerInclude.cardListShimmer.stopShimmer()
                val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo

                if (networkInfo == null || !networkInfo.isConnected) {
                    binding.rVPost.visibility = View.GONE
                    binding.eventsShimmerInclude.root.visibility = View.VISIBLE
                    binding.eventsShimmerInclude.cardListShimmer.startShimmer()
                    binding.fab.visibility=View.GONE
                    Toast.makeText(context, "Network Error!", Toast.LENGTH_SHORT).show()
                }

            },1000)



            firstOpen = false
        }else{
            binding.rVPost.visibility = View.VISIBLE
            binding.fab.visibility=View.VISIBLE
            binding.eventsShimmerInclude.root.visibility = View.GONE
            binding.eventsShimmerInclude.cardListShimmer.stopShimmer()
            recyclerViewAdapter= PostAdapter(list,requireContext())
            binding.rVPost.adapter=recyclerViewAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            firstOpen  = true
//            list.clear()
            val fragment = SocialFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.commit()
            binding.swipeRefreshLayout.isRefreshing = false
        }

    }

}