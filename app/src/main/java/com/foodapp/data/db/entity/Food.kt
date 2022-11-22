package com.foodapp.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "food")
data class Food(
    @PrimaryKey
    val id: Int,
    val title: String,
    val averageRating: Double,
    val description: String,
    val imagePath: String,
    val price: Int,
    val category: String,
    val favourite: Boolean
) : Parcelable