package com.example.shoplist.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "shopItems")
data class ShopItemDBVersion(
    val name :String,
    val count :Int,
    val isChecked :Boolean,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0)