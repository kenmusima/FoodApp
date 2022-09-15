package com.foodapp.ui.fragment.fooditems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foodapp.R
import com.foodapp.data.model.Food
import com.foodapp.databinding.FragmentVeganBinding

class Vegan : Fragment() {

    private var _binding: FragmentVeganBinding? = null
    private val binding: FragmentVeganBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVeganBinding.inflate(inflater, container, false)
        val view = binding.root


        val foods = listOf(
            Food(1,"Lentil Bolognese",4.0,"Very delicious","kdks",9000),
            Food(2,"Bombay Burritos",4.9,"Very delicious","kdks",9000),
            Food(3,"Jägerbraten mit Spätzle",4.0,"Very delicious","kdks",9000),
            Food(4,"Gallo Pinto",4.0,"Very delicious","kdks",9000)
        )

        return view
    }

}