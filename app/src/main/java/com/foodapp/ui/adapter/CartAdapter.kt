package com.foodapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.foodapp.data.db.entity.Order
import com.foodapp.databinding.ListItemCartBinding

class CartAdapter : ListAdapter<Order, CartAdapter.CartViewHolder>(DiffUtilCallback) {

    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {
        fun deleteItemClicked(order: Order)
        fun addItem(order: Order)
        fun removeItem(order: Order)
    }

    fun setListener(listener: OnItemClickListener) {
        this.clickListener = listener
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
            oldItem == newItem
    }

    class CartViewHolder(val binding: ListItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            Glide.with(binding.root)
                .load(order.imagePath)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.itemImage)
            binding.itemName.text = order.title
            binding.itemPrice.text = order.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemCartBinding =
            ListItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(itemCartBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)

        holder.binding.itemChecked.setOnClickListener {
            if (holder.binding.itemChecked.isChecked) {
                clickListener.addItem(order)
            } else {
                clickListener.removeItem(order)
            }
        }
        holder.binding.deleteOrderBtn.setOnClickListener {
            clickListener.deleteItemClicked(order)
        }
    }
}