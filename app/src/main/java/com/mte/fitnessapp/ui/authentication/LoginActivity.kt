package com.mte.fitnessapp.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mte.fitnessapp.ui.home.MainActivity
import com.mte.fitnessapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        auth=FirebaseAuth.getInstance()
        // kullanıcı girip yaptığını kontrol ediyorum
        var currentUser=auth.currentUser
        binding.loginBtn.setOnClickListener {
            var girisemail=binding.email.text.toString()
            var girissifre=binding.password.text.toString()


            if(TextUtils.isEmpty(girisemail)){
                binding.email.error="Please enter your email address!"
                return@setOnClickListener
            }else if(TextUtils.isEmpty(girissifre)){
                binding.password.error="Please enter your password!"
                return@setOnClickListener
            }
            //giris bilgilerini doğrulama
            auth.signInWithEmailAndPassword(girisemail,girissifre)
                .addOnCompleteListener(this) {
                    if(it.isSuccessful){
                        val verification = auth.currentUser?.isEmailVerified
                        if(verification==true) {
                            intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{

                            Toast.makeText(this,"Verify your e-mail address to login", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(applicationContext,"The user information you entered is incorrect!", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, com.mte.fitnessapp.ui.authentication.ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }



}