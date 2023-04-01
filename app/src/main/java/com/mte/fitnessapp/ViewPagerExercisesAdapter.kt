package com.mte.fitnessapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mte.fitnessapp.ui.exercises.*

class ViewPagerExercisesAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 8
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0->{
                AllExercisesFragment()
            }
            1->{
                BicepsFragment()
            }
            2->{
                TricepsFragment()
            }
            3->{
                ShouldersFragment()
            }
            4->{
                ChestFragment()
            }
            5->{
                BackFragment()
            }
            6->{
                AbsFragment()
            }
            7->{
                LegsFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}