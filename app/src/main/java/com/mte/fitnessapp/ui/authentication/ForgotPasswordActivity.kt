package com.mte.fitnessapp.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.mte.fitnessapp.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        auth = FirebaseAuth.getInstance()

        binding.resetBtn.setOnClickListener {
            var psifirlaemail=binding.resetEmail.text.toString().trim()
            if (TextUtils.isEmpty(psifirlaemail)){
                binding.resetEmail.error="Lütfen email adresinizi giriniz"

            }else{
                auth.sendPasswordResetEmail(psifirlaemail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            binding.resetEmail.setText("")
                            binding.resetEmail.error="Şifre sıfırlama maili gönderildi"
                        } else {
                            binding.resetEmail.error="Şifre sıfırlama maili gönderilemedi"
                        }
                    }
            }
        }

        binding.leftArrow.setOnClickListener {
            finish()
        }
    }
}