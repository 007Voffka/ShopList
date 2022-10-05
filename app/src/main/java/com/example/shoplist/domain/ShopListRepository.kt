package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean)
    suspend fun deleteShopItem(id : Int)
    fun getShopList() : LiveData<List<ShopItem>>
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun getShopItem(id : Int) : ShopItem
}