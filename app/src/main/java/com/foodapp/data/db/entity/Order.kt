package com.foodapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val quantity: Int,
    val imagePath: String,
    val price: Int
) {
    override fun toString(): String = "You ordered $title with a quantity of $quantity"
}