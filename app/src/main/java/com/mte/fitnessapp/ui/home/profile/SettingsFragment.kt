package com.mte.fitnessapp.ui.home.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mte.fitnessapp.ui.authentication.LoginActivity
import com.mte.fitnessapp.databinding.FragmentSettingsBinding
import com.squareup.picasso.Picasso

class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
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

        binding.cikisYap.setOnClickListener { auth.signOut()
            val intent= Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.deleteAccount.setOnClickListener {
            currentuser?.delete()?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(),"Hesabınız Silindi", Toast.LENGTH_LONG).show()
                    auth.signOut()
                    val intent= Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                    var currentUserDb=currentuser?.let { it1 -> databaseReference?.child(it1.uid) }
                    currentUserDb?.removeValue()
                }
            }

        }
        binding.resetparola.setOnClickListener {
            var psifirlaemail=currentuser?.email

            if (psifirlaemail != null) {
                auth.sendPasswordResetEmail(psifirlaemail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(requireContext(),"Şifre sıfırlama maili gönderildi", Toast.LENGTH_LONG).show()
                        }
                    }
            }

        }
        binding.buttonGuncelle.setOnClickListener {
          var guncelleEmail=binding.editTextEposta.text.toString().trim()
            currentuser!!.updateEmail(guncelleEmail).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(),"Email Güncellendi",Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(requireContext(),"Email güncelleme başarısız",Toast.LENGTH_SHORT).show()
                }
            }

            var currentUserDb=currentuser?.let { it1->databaseReference?.child(it1.uid) }
            currentUserDb?.removeValue()
            currentUserDb?.child("username")?.setValue(binding.editTextAd.text.toString())
            currentUserDb?.child("email")?.setValue(binding.editTextEposta.text.toString())

            auth.signOut()
            val intent= Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }


    }

}