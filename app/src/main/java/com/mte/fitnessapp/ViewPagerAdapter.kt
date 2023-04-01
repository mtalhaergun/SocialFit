package com.mte.fitnessapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mte.fitnessapp.ui.splashscreen.intro.Intro1Fragment
import com.mte.fitnessapp.ui.splashscreen.intro.Intro2Fragment
import com.mte.fitnessapp.ui.splashscreen.intro.Intro3Fragment


class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = listOf(
        Intro1Fragment(),
        Intro2Fragment(),
        Intro3Fragment()
    )

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}