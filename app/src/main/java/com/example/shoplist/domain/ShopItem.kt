package com.example.shoplist.domain


data class ShopItem(
    val name :String,
    val count :Int,
    val isChecked :Boolean,
    val id : Int = 0)