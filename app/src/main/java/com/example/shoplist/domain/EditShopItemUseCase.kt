package com.example.shoplist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
    ) {
    suspend fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean) {
        return shopListRepository.editShopItem(id, name, count, isChecked)
    }
}