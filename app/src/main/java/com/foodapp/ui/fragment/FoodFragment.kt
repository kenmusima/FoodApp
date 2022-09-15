package com.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foodapp.R
import com.foodapp.databinding.FragmentFoodBinding
import com.foodapp.ui.adapter.ViewPager
import com.google.android.material.tabs.TabLayoutMediator

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null
    private val binding: FragmentFoodBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodBinding.inflate(inflater,container,false)

        val pagerAdapter = ViewPager(this)

        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Local"
                }
                1 -> {
                    tab.text = "International"
                }
                2 -> {
                    tab.text = "Vegan"
                }
                3 -> {
                    tab.text = "Swahili"
                }
                4 -> {
                    tab.text = "Dessert"
                }
            }
        }.attach()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}