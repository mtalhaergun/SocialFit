package com.mte.fitnessapp.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mte.fitnessapp.databinding.ActivitySplashScreenBinding
import com.mte.fitnessapp.ui.splashscreen.intro.IntroActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding.apply {
            splashScreenLogo.alpha = 0f
            splashScreenLogo.animate().setDuration(1500).alpha(1f).withEndAction{
                val intent = Intent(this@SplashScreenActivity, IntroActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }



    }
}