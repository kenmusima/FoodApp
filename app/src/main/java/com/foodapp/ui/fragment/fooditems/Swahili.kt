package com.foodapp.ui.fragment.fooditems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foodapp.R
import com.foodapp.data.model.Food
import com.foodapp.databinding.FragmentSwahiliBinding


class Swahili : Fragment() {

    private var _binding: FragmentSwahiliBinding? = null
    private val binding: FragmentSwahiliBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSwahiliBinding.inflate(inflater,container,false)
        val view = binding.root

        val foods = listOf(
            Food(1,"Ndizi nyama",4.0,"Very delicious","kdks",9000),
            Food(2,"Chipsi Mayai",4.9,"Very delicious","kdks",9000),
            Food(3,"Pilau",4.0,"Very delicious","kdks",9000)
        )

        return view
    }

}