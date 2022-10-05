package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem

class MapperShopItem {
    fun shopItemToDbVersion(shopItem : ShopItem) : ShopItemDBVersion {
        return ShopItemDBVersion(shopItem.name, shopItem.count, shopItem.isChecked, shopItem.id)
    }
    fun shopItemDBVersionToUsual(shopItem : ShopItemDBVersion) : ShopItem {
        return ShopItem(shopItem.name, shopItem.count, shopItem.isChecked, shopItem.id)
    }
    fun shopListDbToUsual(list: List<ShopItemDBVersion>) : List<ShopItem> {
        val newList = mutableListOf<ShopItem>()
        for(item in list) {
            newList.add(shopItemDBVersionToUsual(item))
        }
        return newList
    }
}