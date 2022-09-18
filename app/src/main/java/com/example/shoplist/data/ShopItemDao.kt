package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoplist.domain.ShopItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shopItems")
    fun getAllShopItems() : LiveData<List<ShopItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItem: ShopItem) : Completable

    @Query("SELECT * FROM shopItems WHERE id == :id LIMIT 1")
    fun getShopItem(id : Int) : LiveData<ShopItem>

    @Query("DELETE FROM shopItems WHERE id == :id")
    fun deleteShopItem(id : Int) : Completable

    @Query("UPDATE shopItems SET name = :name, count = :count, isChecked = :isChecked WHERE id = :id")
    fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean) : Completable
}