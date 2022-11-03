package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopItemDao : ShopItemDao,
    private val mapper : MapperShopItem
) : ShopListRepository {

    override suspend fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean){
        return shopItemDao.editShopItem(id, name, count, isChecked)
    }

    override suspend fun deleteShopItem(id: Int) {
        return shopItemDao.deleteShopItem(id)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply {
        addSource(shopItemDao.getAllShopItems()) {
            value = mapper.shopListDbToEntity(it)
        }
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        return shopItemDao.addShopItem(mapper.entityShopItemToDbModel(shopItem))
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        return mapper.shopItemDbToEntity(shopItemDao.getShopItem(id))
    }
}