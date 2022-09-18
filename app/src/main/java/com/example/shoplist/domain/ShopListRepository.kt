package com.example.shoplist.domain

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Completable

interface ShopListRepository {
    fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean) : Completable
    fun deleteShopItem(id : Int) : Completable
    fun getShopList() : LiveData<List<ShopItem>>
    fun addShopItem(shopItem: ShopItem) : Completable
    fun getShopItem(id : Int) : LiveData<ShopItem>
}