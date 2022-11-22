package com.foodapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.foodapp.data.db.entity.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getAll(): Flow<List<Food>>

    @Query("SELECT * FROM food WHERE category LIKE :category")
    fun getFoodByCategory(category: String): Flow<List<Food>>

    @Query("UPDATE food SET favourite =:isFavourite WHERE id =:id")
    fun setFavouriteFood(isFavourite: Boolean, id: Int)

    @Query("SELECT * FROM food WHERE favourite = 1")
    fun getFavourites(): LiveData<List<Food>>

    @Query("DELETE FROM food WHERE id =:id")
    fun deleteFavouriteById(id: Int)
}