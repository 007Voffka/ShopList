package com.example.shoplist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
    ) {
    suspend fun addShopItem(shopItem: ShopItem) {
        return shopListRepository.addShopItem(shopItem)
    }
}