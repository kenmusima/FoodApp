package com.foodapp.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.databinding.FragmentFavoriteBinding
import com.foodapp.ui.SharedViewModel
import com.foodapp.ui.adapter.FavouriteAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.foodapp.ui.adapter.RecyclerAdapter
import com.foodapp.R

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding
        get() = _binding!!

    private val viewModel by viewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        val adapter = FavouriteAdapter()

        viewModel.fetchFavourite().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.listFavourites.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favourite = adapter.currentList[position]
                viewModel.deleteFavourite(favourite.id)
            }

        })

        itemTouchHelper.attachToRecyclerView(binding.listFavourites)
        return binding.root
    }


}