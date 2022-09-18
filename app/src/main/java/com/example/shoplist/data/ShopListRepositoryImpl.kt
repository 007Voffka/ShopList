package com.example.shoplist.data

import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    private val db = ShopItemDatabase.getInstance(application)

    override fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean) : Completable {
        return db.shopItemDao().editShopItem(id, name, count, isChecked)
    }

    override fun deleteShopItem(id: Int) : Completable {
        return db.shopItemDao().deleteShopItem(id)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return db.shopItemDao().getAllShopItems()
    }

    override fun addShopItem(shopItem: ShopItem) : Completable {
        return db.shopItemDao().addShopItem(shopItem)
    }

    override fun getShopItem(id: Int): LiveData<ShopItem> {
        return db.shopItemDao().getShopItem(id)
    }
}