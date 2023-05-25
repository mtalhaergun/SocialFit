package com.mte.fitnessapp.ui.splashscreen.intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mte.fitnessapp.ui.authentication.LoginActivity
import com.mte.fitnessapp.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding
    private lateinit var introFirstOpen : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        introFirstOpen = this.getSharedPreferences("IntroFirstOpen", Context.MODE_PRIVATE)!!

        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(Intro1Fragment())
        adapter.addFragment(Intro2Fragment())
        adapter.addFragment(Intro3Fragment())
        binding.viewpager.adapter = adapter
        binding.indicator.setViewPager2(binding.viewpager)

        binding.getStartedBtn.setOnClickListener{
            introFirstOpen.edit().putBoolean("introfirstopen", false).apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}