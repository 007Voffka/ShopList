package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import javax.inject.Inject

class MapperShopItem @Inject constructor() {
    fun entityShopItemToDbModel(shopItem : ShopItem) : ShopItemDBVersion {
        return ShopItemDBVersion(shopItem.name, shopItem.count, shopItem.isChecked, shopItem.id)
    }
    fun shopItemDbToEntity(shopItem : ShopItemDBVersion) : ShopItem {
        return ShopItem(shopItem.name, shopItem.count, shopItem.isChecked, shopItem.id)
    }
    fun shopListDbToEntity(list: List<ShopItemDBVersion>) : List<ShopItem> {
        val newList = mutableListOf<ShopItem>()
        for(item in list) {
            newList.add(shopItemDbToEntity(item))
        }
        return newList
    }
}