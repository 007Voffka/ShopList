package com.example.shoplist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun addShopItem(shopItem: ShopItem) {
        return shopListRepository.addShopItem(shopItem)
    }
}