package com.foodapp.ui.order

import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.db.entity.Food
import com.foodapp.data.db.entity.Order
import com.foodapp.databinding.FragmentOrderItemBinding
import com.foodapp.utils.currency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderItemFragment : Fragment(R.layout.fragment_order_item) {

    private var _binding: FragmentOrderItemBinding? = null
    private val binding: FragmentOrderItemBinding
        get() = _binding!!

    val args: OrderItemFragmentArgs by navArgs()
    private val viewModel by viewModels<OrderItemViewModel>()

    private lateinit var selectedFood: Food

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderItemBinding.bind(view)
        selectedFood = args.foodItem


        addItemCount()
        subtractItemCount()

        observeOrderCount()
        observeTotalPrice()

        setUp()

        addOrderToCart()

        setUpNav()

        setFavouriteState()

        menuItemListener()
    }

    private fun menuItemListener() {
        binding.toolBarOrder.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.set_favorite -> {
                    menuItem.isChecked = !menuItem.isChecked
                    val stateListDrawable =
                        requireActivity().getDrawable(R.drawable.select_favourite) as StateListDrawable
                    if (menuItem.isChecked) {
                        viewModel.setFavorite(true, selectedFood.id)
                        val state =
                            intArrayOf(android.R.attr.state_checked)
                        stateListDrawable.state = state
                        menuItem.icon = stateListDrawable.current
                    } else {
                        viewModel.setFavorite(false, selectedFood.id)
                        val state = intArrayOf(android.R.attr.state_empty)
                        stateListDrawable.state = state
                        menuItem.icon = stateListDrawable.current
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun setFavouriteState() {
        val stateListDrawable =
            requireActivity().getDrawable(R.drawable.select_favourite) as StateListDrawable
        val state = intArrayOf(android.R.attr.state_checked)

        if (selectedFood.favourite) {
            stateListDrawable.state = state
            binding.toolBarOrder.menu.findItem(R.id.set_favorite).icon = stateListDrawable.current
        }
    }

    private fun setUp() {
        Glide.with(this)
            .load(selectedFood.imagePath)
            .centerCrop()
            .into(binding.itemImage)
        binding.itemTitle.text = selectedFood.title
        binding.itemPrice.text =
            getString(R.string.price_currency, currency.symbol, selectedFood.price)
        binding.itemDescription.text = selectedFood.description
        binding.itemRating.text = selectedFood.averageRating.toString()
    }

    private fun setUpNav() {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.toolBarOrder.setupWithNavController(navController)
    }

    private fun subtractItemCount() {
        binding.subtractItem.setOnClickListener {
            viewModel.subtractCount()
        }
    }

    private fun addItemCount() {
        binding.addItem.setOnClickListener {
            viewModel.addCount()
        }
    }

    private fun observeTotalPrice() {
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.totalPrice.text =
                getString(R.string.price_currency, currency.symbol, it)
        }
    }

    private fun observeOrderCount() {
        viewModel.itemCount.observe(viewLifecycleOwner) {
            binding.subtractItem.isEnabled = it != 0
            binding.itemCount.text = it.toString()
            viewModel.calculateTotal(selectedFood.price, it)
        }
    }

    private fun addOrderToCart() {
        binding.addItemCart.setOnClickListener {
            val price = viewModel.totalPrice.value!!
            val count = viewModel.itemCount.value!!

            lifecycleScope.launch {
                val order = Order(0, selectedFood.title, count, selectedFood.imagePath, price)
                viewModel.saveOrder(order)

                val action = OrderItemFragmentDirections.actionOrderItemFragmentToCart()
                findNavController().navigate(action)
            }
        }
    }


    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}