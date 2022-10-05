package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoplist.domain.ShopItem
import io.reactivex.rxjava3.core.Completable

@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shopItems")
    fun getAllShopItems() : LiveData<List<ShopItemDBVersion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItem: ShopItemDBVersion)

    @Query("SELECT * FROM shopItems WHERE id == :id LIMIT 1")
    suspend fun getShopItem(id : Int) : ShopItemDBVersion

    @Query("DELETE FROM shopItems WHERE id == :id")
    suspend fun deleteShopItem(id : Int)

    @Query("UPDATE shopItems SET name = :name, count = :count, isChecked = :isChecked WHERE id = :id")
    suspend fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean)
}