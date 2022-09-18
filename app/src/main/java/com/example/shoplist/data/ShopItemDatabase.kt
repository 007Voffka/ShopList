package com.example.shoplist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoplist.domain.ShopItem

@Database(entities = [ShopItem::class], version = 1, exportSchema = false)
abstract class ShopItemDatabase : RoomDatabase() {
    companion object {
        private const val TABLE_NAME = "shopItems.db"
        private var db: ShopItemDatabase? = null
        private var LOCK = Any()

        fun getInstance(context: Context) : ShopItemDatabase {
            synchronized(LOCK) {
                db?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    context,
                ShopItemDatabase::class.java,
                TABLE_NAME)
                    .build()
                db = instance
                return instance
            }
        }
    }
    abstract fun shopItemDao() : ShopItemDao
}