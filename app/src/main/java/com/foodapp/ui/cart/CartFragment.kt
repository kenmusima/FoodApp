package com.foodapp.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.db.entity.Order
import com.foodapp.utils.currency
import com.foodapp.databinding.FragmentCartBinding
import com.foodapp.ui.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.OnItemClickListener {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding
        get() = _binding!!

    private val viewModel by viewModels<CartViewModel>()
    private val adapter = CartAdapter()

    private var totalPrice = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        setUp()
        observeCart()
        checkoutItems()
        recyclerTouchHelper()
    }

    private fun setUp() {
        adapter.apply { setListener(this@CartFragment) }

        binding.txtTotalPrice.text = getString(R.string.price_currency, currency.symbol, 0)

        lifecycleScope.launch {
            viewModel.retrieveOrders().collectLatest {
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                    binding.noItems.root.visibility = View.GONE
                    binding.bottomContainerCart.visibility = View.VISIBLE
                    binding.ordersList.visibility = View.VISIBLE
                } else {
                    binding.noItems.root.visibility = View.VISIBLE
                    binding.bottomContainerCart.visibility = View.GONE
                    binding.ordersList.visibility = View.GONE
                }
            }
        }
        binding.ordersList.layoutManager = LinearLayoutManager(requireContext())
        binding.ordersList.adapter = adapter
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
                val order = adapter.currentList[position]
                viewModel.deleteByOrderId(order)
                totalPrice -= order.price
                binding.txtTotalPrice.text =
                    getString(R.string.price_currency, currency.symbol, totalPrice)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.ordersList)
    }

    private fun checkoutItems() {
        binding.btnCheckout.setOnClickListener {
            val action = viewModel.itemsSelected.value?.toIntArray()
                ?.let { it1 -> CartFragmentDirections.actionCartToPaymentFragment(it1) }
            if (action != null) {
                findNavController().navigate(action)
            }
        }
    }

    private fun observeCart() {
        viewModel.itemsSelected.observe(viewLifecycleOwner) {
            binding.btnCheckout.isEnabled = it.isNotEmpty()
        }
    }

    override fun deleteItemClicked(order: Order) {
        totalPrice -= order.price
        binding.txtTotalPrice.text = getString(R.string.price_currency, currency.symbol, totalPrice)
        viewModel.deleteByOrderId(order)
    }

    override fun addItem(order: Order) {
        totalPrice += order.price
        binding.txtTotalPrice.text = getString(R.string.price_currency, currency.symbol, totalPrice)
        viewModel.addItem(order)
    }

    override fun removeItem(order: Order) {
        totalPrice -= order.price
        binding.txtTotalPrice.text = getString(R.string.price_currency, currency.symbol, totalPrice)
        viewModel.removeItem(order)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}