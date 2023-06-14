package com.mte.fitnessapp.ui.home.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentPhotoDetailBinding
import com.mte.fitnessapp.ui.home.social.SocialFragmentDirections
import com.squareup.picasso.Picasso

class PhotoDetailFragment : Fragment() {

    private var _binding: FragmentPhotoDetailBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: PhotoDetailFragmentArgs by navArgs()
        val data = args.post
        binding.publisherUserNamePost.text = data.userName
        binding.publisher.text = data.userName
        binding.postCaption.text = data.caption
        Picasso.get().load(data.imageUrl).into(binding.postImage)
        db.collection("photos").document(data.id).collection("likes")
            .addSnapshotListener { value, error ->
                val values = value!!.documents
                binding.likes.text ="${values.size} likes"
            }


        val database = FirebaseDatabase.getInstance()
        val databaseReference = database?.reference!!.child("profile")
        var currentuser = auth.currentUser
        var userReference = databaseReference?.child(currentuser?.uid!!)
        var userName = ""

        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var profilphoto = (snapshot.child("profilPhoto").value.toString())
                if (profilphoto!="null"){
                    Picasso.get().load(profilphoto).into(binding.publisherProfileImagePost)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.postImageCommentBtn.setOnClickListener {


            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Comment")
            val text = EditText(requireContext())
            builder.setView(text)
            builder.setPositiveButton("Publish") { _, _ ->
                userReference?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        userName = (snapshot.child("username").value.toString())

                        val comment = hashMapOf(
                            "userId" to currentuser?.uid!!,
                            "postId" to data.id,
                            "userName" to userName,
                            "comment" to text.text.toString(),
                            "commentDate" to Timestamp.now()
                        )
                        Toast.makeText(requireContext(), text.text, Toast.LENGTH_LONG).show()
                        db.collection("posts").addSnapshotListener { value, error ->

                            val documents = value!!.documents
                            documents.forEach { it2 ->
                                db.collection("posts").document(it2.id).collection("photos")
                                    .addSnapshotListener { value2, error ->
                                        val photos = value2!!.documents
                                        photos.forEach {
                                            if (it.id == data.id) {
                                                db.collection("posts")
                                                    .document(it2.id)
                                                    .collection("photos")
                                                    .document(data.id).collection("comment")
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
            val alert = builder.create()
            alert.show()
        }


        binding.viewComments.setOnClickListener {
            val action =
                PhotoDetailFragmentDirections.actionPhotoDetailFragmentToCommentFragment(data.id)
            findNavController().navigate(action)

        }


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }



        binding.menuButton.setOnClickListener {
            val popUpMenu = PopupMenu(requireContext(), it)
            popUpMenu.inflate(R.menu.photo_menu)

            popUpMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.deleteMenu -> {
                        db.collection("posts")
                            .document(currentuser!!.uid)
                            .collection("photos").document(data.id).delete().addOnSuccessListener {
                                db.collection("photos").document(data.id).delete()
                                    .addOnSuccessListener {
                                        findNavController().navigate(R.id.action_photoDetailFragment_to_profileFragment)
                                    }
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