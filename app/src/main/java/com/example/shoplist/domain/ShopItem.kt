package com.example.shoplist.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopItems")
data class ShopItem(
    val name :String,
    val count :Int,
    val isChecked :Boolean,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0)