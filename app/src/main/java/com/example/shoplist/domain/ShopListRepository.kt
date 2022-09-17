package com.example.shoplist.domain

interface ShopListRepository {
    fun editShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun getShopList() : List<ShopItem>
    fun addShopItem(shopItem: ShopItem)
    fun getShopItem(id : Int) : ShopItem
}