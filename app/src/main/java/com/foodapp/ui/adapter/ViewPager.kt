package com.foodapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.foodapp.ui.food.types.*

class ViewPager(fa: Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Local()
            1 -> International()
            2 -> Vegan()
            3 -> Swahili()
            4 -> Dessert()
            else -> throw Throwable("Invalid position $position")
        }
    }
}