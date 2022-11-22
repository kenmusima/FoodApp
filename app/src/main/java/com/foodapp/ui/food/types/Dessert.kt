package com.foodapp.ui.food.types

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodapp.R
import com.foodapp.databinding.FragmentFoodListBinding
import com.foodapp.ui.SharedViewModel
import com.foodapp.ui.adapter.RecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Dessert : Fragment(R.layout.fragment_food_list) {

    private var _binding: FragmentFoodListBinding? = null
    private val binding: FragmentFoodListBinding
        get() = _binding!!

    val adapter = RecyclerAdapter()

    private val viewModel by viewModels<SharedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFoodListBinding.bind(view)

        binding.listFoods.layoutManager = LinearLayoutManager(requireContext())


        lifecycleScope.launch {
            viewModel.fetchFoodCategory("dessert").collectLatest {
                adapter.submitList(it)
            }
        }

        binding.listFoods.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}