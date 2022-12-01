package com.foodapp.data.repository

import androidx.lifecycle.LiveData
import com.foodapp.data.db.entity.Food
import com.foodapp.data.db.entity.Order
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoodByCategory(category: String): Flow<List<Food>>
    fun getAllFood(): Flow<List<Food>>
    fun setFavouriteFood(isFavourite: Boolean, id: Int)
    fun getFavouriteFoods(): LiveData<List<Food>>
    fun deleteFavouriteById(id: Int)

    suspend fun saveOrder(order: Order)
    fun getOrders(): Flow<List<Order>>
    fun totalPrice(): LiveData<Int>

    suspend fun deleteByOrderId(orderId: Int)
    fun getSelectedOrders(ids: List<Int>): Flow<List<Order>>
}