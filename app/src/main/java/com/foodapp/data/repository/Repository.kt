package com.foodapp.data.repository

import androidx.lifecycle.LiveData
import com.foodapp.data.db.entity.Food
import com.foodapp.data.db.dao.FoodDao
import com.foodapp.data.db.entity.Order
import com.foodapp.data.db.dao.OrderDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val foodDao: FoodDao, private val orderDao: OrderDao) :
    FoodRepository {
    override fun getFoodByCategory(category: String): Flow<List<Food>> = foodDao.getFoodByCategory(category)
    override fun getAllFood(): Flow<List<Food>> = foodDao.getAll()
    override fun setFavouriteFood(isFavourite: Boolean, id: Int) = foodDao.setFavouriteFood(isFavourite, id)
    override fun getFavouriteFoods(): LiveData<List<Food>> = foodDao.getFavourites()
    override fun deleteFavouriteById(id: Int) = foodDao.deleteFavouriteById(id)

    override suspend fun saveOrder(order: Order) = orderDao.insertOrder(order)
    override fun getOrders(): Flow<List<Order>> = orderDao.getAll()
    override fun totalPrice(): LiveData<Int> = orderDao.totalPrice()
    override suspend fun deleteByOrderId(orderId: Int) = orderDao.deleteByIdOrder(orderId)

    override fun getSelectedOrders(ids: List<Int>): Flow<List<Order>> = orderDao.getSelectedOrders(ids)
}