package com.foodapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foodapp.data.db.dao.FoodDao
import com.foodapp.data.db.dao.OrderDao
import com.foodapp.data.db.entity.Food
import com.foodapp.data.db.entity.Order

@Database(entities = [Food::class, Order::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun ordersDao(): OrderDao
}