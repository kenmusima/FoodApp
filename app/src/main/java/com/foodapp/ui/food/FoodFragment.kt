package com.foodapp.ui.food

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.foodapp.R
import com.foodapp.databinding.FragmentFoodBinding
import com.foodapp.ui.adapter.ViewPager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodFragment : Fragment(R.layout.fragment_food) {

    private var _binding: FragmentFoodBinding? = null
    private val binding: FragmentFoodBinding
        get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFoodBinding.bind(view)
        setUp()
    }

    private fun setUp() {
        auth = Firebase.auth
        val pagerAdapter = ViewPager(this)

        binding.viewPager.adapter = pagerAdapter
        val username = auth.currentUser!!.displayName!!.split(" ")[0]
        binding.userGreetings.text = getString(R.string.user_greetings, username)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}