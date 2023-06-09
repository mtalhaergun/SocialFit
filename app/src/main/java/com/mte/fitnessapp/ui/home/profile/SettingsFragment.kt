package com.mte.fitnessapp.ui.home.profile

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mte.fitnessapp.ui.authentication.LoginActivity
import com.mte.fitnessapp.databinding.FragmentSettingsBinding
import com.squareup.picasso.Picasso

class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    val db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentuser = auth.currentUser
        var userReference = databaseReference?.child(currentuser?.uid!!)


        userReference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.editTextEposta.setText(currentuser!!.email).toString()
                binding.editTextAd.setText(snapshot.child("username").value.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        binding.cikisYap.setOnClickListener {
            auth.signOut()
            val intent= Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.deleteAccount.setOnClickListener {
            currentuser?.delete()?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(),"Your account has been deleted!", Toast.LENGTH_LONG).show()
                    auth.signOut()
                    val intent= Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                    var currentUserDb=currentuser?.let { it1 -> databaseReference?.child(it1.uid) }
                    currentUserDb?.removeValue()
                }else{
                    Toast.makeText(requireContext(),"Your account could not be deleted", Toast.LENGTH_LONG).show()
                }
            }

        }
        binding.resetparola.setOnClickListener {
            var psifirlaemail=currentuser?.email

            if (psifirlaemail != null) {
                auth.sendPasswordResetEmail(psifirlaemail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(),"Password reset mail has been sent", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(requireContext(),"Failed to send password reset email", Toast.LENGTH_LONG).show()
                        }
                    }
            }

        }
        binding.buttonGuncelle.setOnClickListener {
            var currentUserDb=currentuser?.let { it1->databaseReference?.child(it1.uid) }


            var guncelleEmail=binding.editTextEposta.text.toString().trim()
            if(binding.editTextAd.text.toString()!=currentuser?.email && guncelleEmail != ""){
                currentuser!!.updateEmail(guncelleEmail).addOnCompleteListener {
                    if (it.isSuccessful){
                        currentUserDb?.child("email")?.setValue(binding.editTextEposta.text.toString())
                        currentUserDb?.child("username")?.setValue(binding.editTextAd.text.toString())
                        Toast.makeText(requireContext(),"Updated",Toast.LENGTH_SHORT).show()
                        auth.signOut()
                        val intent = Intent(
                            requireContext(),
                            LoginActivity::class.java
                        )
                        startActivity(intent)

                    }else{
                        Toast.makeText(requireContext(),"Update failed!",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(requireContext(),"Update failed!",Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteProfilePicture.setOnClickListener {
            userReference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val networkInfo = connectivityManager.activeNetworkInfo

                    if (networkInfo == null || !networkInfo.isConnected) {

                        Toast.makeText(context, "Deletion failed!", Toast.LENGTH_SHORT).show()
                    }else{
                        val profilPhotoRef = snapshot.child("profilPhoto")
                        profilPhotoRef.ref.removeValue()
                        Toast.makeText(context, "Deletion successful!", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


    }

}