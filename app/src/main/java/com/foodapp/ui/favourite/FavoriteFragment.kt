package com.foodapp.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.databinding.FragmentFavoriteBinding
import com.foodapp.ui.SharedViewModel
import com.foodapp.ui.adapter.FavouriteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding
        get() = _binding!!

    private val viewModel by viewModels<SharedViewModel>()
    val adapter = FavouriteAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)
        observeFavouriteList()
        binding.listFavourites.adapter = adapter
        recyclerTouchHelper()
    }

    private fun observeFavouriteList() {
        viewModel.fetchFavourite().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun recyclerTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}