package com.example.shoplist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)
    private val compositeDisposable = CompositeDisposable()

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopListUseCase = DeleteShopItemUseCase(repository)
    private val editShopListUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    fun getShopList() : LiveData<List<ShopItem>> {
        return getShopListUseCase.getShopList()
    }

    fun getShopItem(itemId : Int): LiveData<ShopItem> {
        return getShopItemUseCase.getShopItem(itemId)
    }

    fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean) {
        val disposable = editShopListUseCase.editShopItem(id, name, count, isChecked)
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, {
                Log.i("Here is exception", it.toString())
            })
        compositeDisposable.add(disposable)
    }

    fun deleteShopItem(itemId : Int) {
        val disposable = deleteShopListUseCase.deleteShopItem(itemId)
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, {
                Log.i("Here is exception", it.toString())
            })
        compositeDisposable.add(disposable)
    }

    fun addShopItem(shopItem: ShopItem) {
        val disposable = addShopItemUseCase.addShopItem(shopItem)
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, {
                Log.i("Here is exception", it.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}