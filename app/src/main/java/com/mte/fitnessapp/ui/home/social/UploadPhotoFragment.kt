package com.mte.fitnessapp.ui.home.social

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
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentUploadPhotoBinding
import java.util.UUID


class UploadPhotoFragment : Fragment() {
    private var _binding : FragmentUploadPhotoBinding? = null
    private val binding get() = _binding!!

    lateinit var imageUri: Uri

    val storageRef = Firebase.storage.reference
    val db= Firebase.firestore
    private lateinit var auth: FirebaseAuth

    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        _binding=FragmentUploadPhotoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button2.isEnabled=false

        val resim = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageUri = Uri.parse(uri.toString())
                binding.imageView2.setImageURI(imageUri)
            }
        }

        binding.imageView2.setOnClickListener {
            resim.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            binding.button2.isEnabled=true
        }

        binding.button2.setOnClickListener {


                val uuid = UUID.randomUUID()
                val imageName = "${uuid}.jpg"
                val imageRef = storageRef.child("images/${auth.uid}/${imageName}")
                val uploadTask = imageRef.putFile(imageUri)
                uploadTask.addOnSuccessListener {
                    val resimLink = imageRef
                    var downloadPhoto = ""

                    resimLink.downloadUrl.addOnSuccessListener {
                        downloadPhoto = it.toString()

                        var currentuser = auth.currentUser
                        var userReference = databaseReference?.child(currentuser?.uid!!)
                        var userName = ""
                        userReference?.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {

                                userName = (snapshot.child("username").value.toString())

                                val field = hashMapOf(
                                    "userMail" to auth.currentUser!!.email,
                                    "userName" to userName
                                )

                                val post = hashMapOf(
                                    "userName" to userName,
                                    "imageUrl" to downloadPhoto,
                                    "uploadDate" to Timestamp.now(),
                                    "caption" to binding.commentEditTextText.text.toString()

                                )
                                db.collection("photos").document(uuid.toString()).set(post)
                                db.collection("posts")
                                    .document(auth.uid!!).set(field).addOnCompleteListener {
                                        db.collection("posts")
                                            .document(auth.uid!!).collection("photos")
                                            .document(uuid.toString()).set(post)
                                            .addOnCompleteListener {
                                                db.collection("posts")
                                                    .document(auth.uid!!)
                                                    .collection("photos")
                                                    .document(uuid.toString()).collection("comment")
                                            }
                                    }

                            }

                            override fun onCancelled(error: DatabaseError) {

                            }
                        })

                        Toast.makeText(requireContext(), "Upload Successful", Toast.LENGTH_SHORT)
                            .show()
                        binding.imageView2.setImageResource(R.drawable.upload_photo)
                        binding.button2.isEnabled=false



                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "Upload Error", Toast.LENGTH_SHORT).show()
                    }
                }



        }
        binding.button3.setOnClickListener {
            findNavController().navigate(R.id.action_uploadPhotoFragment_to_socialFragment)
        }


    }

}