package com.mte.fitnessapp.ui.home.profile

import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentProfileBinding
import com.mte.fitnessapp.model.post.Post
import com.mte.fitnessapp.ui.home.social.SocialFragment
import com.squareup.picasso.Picasso
import java.util.UUID

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    val storageRef = Firebase.storage.reference
    val db= Firebase.firestore
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    private lateinit var adapterPhotos : PhotosRecyclerAdapter
    var list = ArrayList<Post>()


    lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.clear()
        databaseReference=database?.reference!!.child("profile")
        var userReference = databaseReference?.child(auth.uid!!)
        var profilPhoto=""
        var userName=""
        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                profilPhoto = (snapshot.child("profilPhoto").value.toString())
                userName = (snapshot.child("username").value.toString())
                binding.profileName.text=userName
                if (profilPhoto!="null"){
                    Picasso.get().load(profilPhoto).into(binding.profilPhoto)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        val resim = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageUri = Uri.parse(uri.toString())
                val uuid = UUID.randomUUID()
                val imageName = "${uuid}.jpg"
                val imageRef = storageRef.child("images/${auth.uid}/profilphoto/${imageName}")
                val uploadTask = imageRef.putFile(imageUri)
                uploadTask.addOnSuccessListener {
                    val resimLink = imageRef
                    var downloadPhoto = ""
                    resimLink.downloadUrl.addOnSuccessListener {
                        downloadPhoto = it.toString()
                        val currentUser = auth.currentUser
                        val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                        currentUserDb?.child("profilPhoto")?.setValue(downloadPhoto)
                        binding.profilPhoto.setImageURI(imageUri)
                    }
                }
            }
        }
        binding.profilPhoto.setOnClickListener {
            resim.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }

        binding.settingsImage.setOnClickListener {

            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }

        db.collection("posts").document(auth.uid!!).collection("photos")
            .orderBy("uploadDate",com.google.firebase.firestore.Query.Direction.DESCENDING).get().addOnSuccessListener {
                if (it != null) {
                    it.documents.forEach {
                        list.add(Post("${it.id}","${it.getField<String>("userName")}",
                            "${it.getField<String>("imageUrl")}",
                            "${it.getField<String>("caption")}",
                            auth.uid!!))
                        adapterPhotos.notifyDataSetChanged()
                    }
                }
            }

        adapterPhotos = PhotosRecyclerAdapter(list)
        binding.photoRv.adapter = adapterPhotos

        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            list.clear()
            binding.photoRv.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            Toast.makeText(context, "Network Error!", Toast.LENGTH_SHORT).show()
        }

        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        binding.photoRv.addItemDecoration(GridSpacingItemDecoration(3, spacing))

        binding.swipeRefreshLayout.setOnRefreshListener {
            val fragment = ProfileFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.commit()
            binding.swipeRefreshLayout.isRefreshing = false
        }

    }

    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }

}
