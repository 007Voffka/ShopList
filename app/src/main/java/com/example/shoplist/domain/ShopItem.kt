package com.example.shoplist.domain

data class ShopItem(
    val name :String,
    val id : Int,
    val count :Int,
    val isBought :Boolean)