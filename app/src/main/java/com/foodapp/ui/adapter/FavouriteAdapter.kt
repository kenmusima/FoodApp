package com.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.foodapp.R
import com.foodapp.data.db.entity.Food
import com.foodapp.databinding.ListItemFoodBinding
import com.foodapp.ui.favourite.FavoriteFragmentDirections
import com.foodapp.utils.currency

class FavouriteAdapter : ListAdapter<Food, FavouriteAdapter.ViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Food, newItem: Food) = oldItem == newItem
    }

    class ViewHolder(private val binding: ListItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            Glide.with(binding.root)
                .load(food.imagePath)
                .centerCrop()
                .placeholder(R.drawable.placeholder_animation)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.foodImage)
            val formattedPrice = binding.root.context.getString(R.string.price_currency, currency.symbol, food.price)

            binding.foodTitle.text = food.title
            binding.foodPrice.text = formattedPrice
            binding.foodRatingTxt.text = food.averageRating.toString()
            binding.itemAdd.setOnClickListener {
                val action = FavoriteFragmentDirections.actionFavoriteToOrderItemFragment(food)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ListItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPosition = getItem(position)
        holder.bind(itemPosition)
    }
}