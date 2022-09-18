package com.example.shoplist.domain

import androidx.lifecycle.LiveData

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(id : Int) : LiveData<ShopItem> {
        return shopListRepository.getShopItem(id)
    }
}