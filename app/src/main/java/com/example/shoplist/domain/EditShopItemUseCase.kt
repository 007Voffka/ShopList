package com.example.shoplist.domain

import io.reactivex.rxjava3.core.Completable

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean) : Completable {
        return shopListRepository.editShopItem(id, name, count, isChecked)
    }
}