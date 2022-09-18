package com.example.shoplist.domain

import io.reactivex.rxjava3.core.Completable

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun deleteShopItem(id : Int) : Completable {
        return shopListRepository.deleteShopItem(id)
    }
}