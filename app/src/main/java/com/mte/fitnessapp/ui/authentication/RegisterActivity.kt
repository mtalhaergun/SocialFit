package com.mte.fitnessapp.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mte.fitnessapp.databinding.ActivityLoginBinding
import com.mte.fitnessapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
        binding.signupBtn.setOnClickListener {
            var uyeadsoyad=binding.username.text.toString()
            var uyesifre=binding.password.text.toString()
            var uyemail=binding.email.text.toString()
            if(TextUtils.isEmpty(uyeadsoyad)){
                binding.username.error="Lütfen Adınızı ve Soyadınızı Giriniz"
                return@setOnClickListener

            }else if(TextUtils.isEmpty(uyesifre)){
                binding.username.error="Lütfen Şifre Belirleyiniz"
                return@setOnClickListener

            }else if(TextUtils.isEmpty(uyemail)) {
                binding.email.error = "Lütfen Şifre Belirleyiniz"
                return@setOnClickListener
            }
            //Üye Bilgilerini veri tabanına ekleme
            auth.createUserWithEmailAndPassword(binding.email.text.toString(),binding.password.text.toString())
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){

                        val currentUser = auth.currentUser
                        val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                        currentUserDb?.child("username")?.setValue(uyeadsoyad)
                        currentUserDb?.child("email")?.setValue(uyemail)
                        currentUserDb?.child("sifre")?.setValue(uyesifre)
                        Toast.makeText(this,"Kayıt Başarılı",Toast.LENGTH_SHORT).show()
                        auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {

                            Toast.makeText(this,"Mailinizi Kontrol Ediniz",Toast.LENGTH_LONG).show()
                        }?.addOnFailureListener{
                            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                        }

                        auth.signOut()
                    }else{
                        Toast.makeText(this,"Hata Oluştu",Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.alreadyHave.setOnClickListener {
            finish()
        }
    }
}