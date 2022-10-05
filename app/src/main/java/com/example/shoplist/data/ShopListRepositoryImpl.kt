package com.example.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import io.reactivex.rxjava3.core.Completable

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    private val db = ShopItemDatabase.getInstance(application)
    private val mapper = MapperShopItem()

    override suspend fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean){
        return db.shopItemDao().editShopItem(id, name, count, isChecked)
    }

    override suspend fun deleteShopItem(id: Int) {
        return db.shopItemDao().deleteShopItem(id)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply {
        addSource(db.shopItemDao().getAllShopItems()) {
            value = mapper.shopListDbToUsual(it)
        }
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        return db.shopItemDao().addShopItem(mapper.shopItemToDbVersion(shopItem))
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        return mapper.shopItemDBVersionToUsual(db.shopItemDao().getShopItem(id))
    }
}