package com.example.shoplist.di

import android.app.Application
import com.example.shoplist.data.ShopItemDao
import com.example.shoplist.data.ShopItemDatabase
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl : ShopListRepositoryImpl) : ShopListRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideDb(application: Application) : ShopItemDao {
            return ShopItemDatabase.getInstance(application).shopItemDao()
        }
    }
}