package com.foodapp.data.repository

import androidx.lifecycle.LiveData
import com.foodapp.data.db.entity.Food
import com.foodapp.data.db.dao.FoodDao
import com.foodapp.data.db.entity.Order
import com.foodapp.data.db.dao.OrderDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val foodDao: FoodDao, private val orderDao: OrderDao) {
    fun getFoodByCategory(category: String): Flow<List<Food>> = foodDao.getFoodByCategory(category)
    fun getAllFood(): Flow<List<Food>> = foodDao.getAll()
    fun setFavouriteFood(isFavourite: Boolean, id: Int) = foodDao.setFavouriteFood(isFavourite, id)
    fun getFavouriteFoods(): LiveData<List<Food>> = foodDao.getFavourites()
    fun deleteFavouriteById(id: Int) = foodDao.deleteFavouriteById(id)

    suspend fun saveOrder(order: Order) = orderDao.insertOrder(order)
    fun getOrders(): Flow<List<Order>> = orderDao.getAll()
    fun totalPrice(): LiveData<Int> = orderDao.totalPrice()
    suspend fun deleteByOrderId(orderId: Int) = orderDao.deleteByIdOrder(orderId)

    fun getSelectedOrders(ids: List<Int>): Flow<List<Order>> = orderDao.getSelectedOrders(ids)
}