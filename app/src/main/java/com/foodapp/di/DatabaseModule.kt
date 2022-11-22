package com.foodapp.di

import android.content.Context
import androidx.room.Room
import com.foodapp.data.db.AppDatabase
import com.foodapp.data.db.dao.FoodDao
import com.foodapp.data.db.dao.OrderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "foodapp_db")
            .createFromAsset("database/food_db.db")
            .build()

    @Provides
    @Singleton
    fun providesDao(appDatabase: AppDatabase): FoodDao =
        appDatabase.foodDao()

    @Provides
    @Singleton
    fun providesOrderDao(appDatabase: AppDatabase): OrderDao =
        appDatabase.ordersDao()
}