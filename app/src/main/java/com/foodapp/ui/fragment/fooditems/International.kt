package com.foodapp.ui.fragment.fooditems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodapp.data.model.Food
import com.foodapp.databinding.FragmentInternationalBinding
import com.foodapp.ui.adapter.RecyclerAdapter

class International : Fragment() {

    private var _binding: FragmentInternationalBinding? = null
    private val binding: FragmentInternationalBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInternationalBinding.inflate(inflater,container,false)

        binding.listFoods.layoutManager = LinearLayoutManager(requireContext())
        val adapter = RecyclerAdapter()

        val foods = listOf(Food(1,"Samosa",4.0,"Very delicious","kdks",9000),
            Food(2,"Sausage",4.9,"Very delicious","kdks",9000),
            Food(3,"Pilau",4.0,"Very delicious","kdks",9000),
            Food(4,"Sosa",4.0,"Very delicious","kdks",9000),
            Food(5,"Biriyani",4.0,"Very delicious","kdks",9000))
        adapter.submitList(foods)

        binding.listFoods.adapter = adapter
        return binding.root
    }
}