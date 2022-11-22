package com.foodapp.ui.payment

import androidx.lifecycle.ViewModel
import com.foodapp.data.repository.Repository
import com.foodapp.data.db.entity.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun setData(intArray: IntArray): Flow<List<Order>> = repository.getSelectedOrders(intArray.toMutableList())
}