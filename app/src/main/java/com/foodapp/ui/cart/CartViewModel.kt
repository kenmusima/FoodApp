package com.foodapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.data.db.entity.Order
import com.foodapp.data.repository.Repository
import com.foodapp.utils.notifyObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _itemsSelected: MutableLiveData<ArrayList<Int>> by lazy {
        MutableLiveData<ArrayList<Int>>().apply {
            value = ArrayList()
        }
    }
    val itemsSelected: LiveData<ArrayList<Int>>
        get() = _itemsSelected

    fun retrieveOrders() = repository.getOrders()
    fun totalItemPrices() = repository.totalPrice()
    fun deleteByOrderId(order: Order) =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteByOrderId(order.id) }

    fun addItem(order: Order) {
        if (_itemsSelected.value?.contains(order.id) == true) return
        _itemsSelected.value?.add(order.id)
        _itemsSelected.notifyObserver()
    }

    fun removeItem(order: Order) {
        _itemsSelected.value?.remove(order.id)
        _itemsSelected.notifyObserver()
    }
}