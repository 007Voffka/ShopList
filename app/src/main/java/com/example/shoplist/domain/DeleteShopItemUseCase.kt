package com.example.shoplist.domain

import io.reactivex.rxjava3.core.Completable

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun deleteShopItem(id : Int) {
        return shopListRepository.deleteShopItem(id)
    }
}