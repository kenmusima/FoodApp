package com.foodapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foodapp.data.db.entity.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders")
    fun getAll(): Flow<List<Order>>

    @Query("DELETE FROM orders WHERE id = :orderId")
    suspend fun deleteByIdOrder(orderId: Int)

    @Query("SELECT * FROM orders WHERE id IN (:ids)")
    fun getSelectedOrders(ids: List<Int>): Flow<List<Order>>

    @Insert
    suspend fun insertOrder(order: Order)

    @Delete
    fun deleteOrder(order: Order)

    @Query("SELECT SUM(price) FROM orders")
    fun totalPrice(): LiveData<Int>
}