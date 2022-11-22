package com.foodapp.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.data.repository.Repository
import com.foodapp.data.db.entity.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class OrderItemViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int>
        get() = _totalPrice

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int>
        get() = _itemCount

    init {
        _itemCount.value = 0
    }

    fun saveOrder(order: Order) =
        viewModelScope.launch(Dispatchers.IO) { repository.saveOrder(order) }

    fun setFavorite(isFavourite: Boolean, id: Int) =
        viewModelScope.launch(Dispatchers.IO) { repository.setFavouriteFood(isFavourite, id) }

    fun addCount() {
        _itemCount.value = _itemCount.value?.plus(1)
    }

    fun subtractCount() {
        _itemCount.value = _itemCount.value?.minus(1)?.let { abs(it) }
    }

    fun calculateTotal(itemPrice: Int, itemCount: Int) {
        if (itemCount < 0) return
        _totalPrice.value = itemPrice * itemCount
    }
}