package com.foodapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.foodapp.ui.fragment.fooditems.*

class ViewPager(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle){
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LocalFragment()
            1 -> InternationalFragment()
            2 -> Vegan()
            3 -> Swahili()
            4 -> Dessert()
            else -> throw Throwable("Invalid position $position")
        }
    }
}