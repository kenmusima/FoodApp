package com.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.foodapp.R
import com.foodapp.utils.currency
import com.foodapp.data.db.entity.Food
import com.foodapp.databinding.ListItemFoodBinding
import com.foodapp.ui.food.FoodFragmentDirections

class RecyclerAdapter : ListAdapter<Food, RecyclerAdapter.RecyclerViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Food, newItem: Food) = oldItem == newItem
    }

    class RecyclerViewHolder(val binding: ListItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            Glide.with(binding.root)
                .load(food.imagePath)
                .centerCrop()
                .placeholder(R.drawable.placeholder_animation)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.foodImage)
            val formattedPrice = binding.root.context.getString(R.string.price_currency, currency.symbol, food.price)

            binding.foodTitle.text = food.title
            binding.foodPrice.text = formattedPrice
            binding.foodRatingTxt.text = food.averageRating.toString()
            binding.itemAdd.setOnClickListener {

                val action = FoodFragmentDirections.actionHomeToOrderItemFragment(food)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemFoodBinding = ListItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(itemFoodBinding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }
}