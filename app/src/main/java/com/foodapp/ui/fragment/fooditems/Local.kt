package com.foodapp.ui.fragment.fooditems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foodapp.R
import com.foodapp.data.model.Food
import com.foodapp.databinding.FragmentLocalBinding


class Local : Fragment() {

    private var _binding: FragmentLocalBinding? = null
    private val binding: FragmentLocalBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLocalBinding.inflate(inflater,container,false)
        val view = binding.root

        val foods = listOf(
            Food(1,"Githeri",4.0,"Very delicious","kdks",9000),
            Food(2,"Mokimo",4.9,"Very delicious","kdks",9000),
            Food(3,"Matoke",4.0,"Very delicious","kdks",9000),
            Food(4,"Chicken tikka masala",4.0,"Very delicious","kdks",9000)
        )

        return view
    }
}