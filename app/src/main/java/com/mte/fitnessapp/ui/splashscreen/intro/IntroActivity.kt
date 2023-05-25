package com.mte.fitnessapp.ui.splashscreen.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mte.fitnessapp.ui.authentication.LoginActivity
import com.mte.fitnessapp.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(Intro1Fragment())
        adapter.addFragment(Intro2Fragment())
        adapter.addFragment(Intro3Fragment())
        binding.viewpager.adapter = adapter
        binding.indicator.setViewPager2(binding.viewpager)

        binding.getStartedBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}