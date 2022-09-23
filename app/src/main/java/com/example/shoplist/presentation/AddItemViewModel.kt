package com.example.shoplist.presentation

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)
    private val compositeDisposable = CompositeDisposable()

    private val editShopListUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName : LiveData<Boolean>
    get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldCloseScreen = MutableLiveData<Boolean>()
    val shouldCloseScreen : LiveData<Boolean>
        get() = _shouldCloseScreen


    fun getShopItem(id : Int) : LiveData<ShopItem> {
        return getShopItemUseCase.getShopItem(id)
    }

    fun addShopItem(name : Editable, count : Editable) {
        if (checkNameIsCorrect(name) && checkCountIsCorrect(count)) {
            val correctName = name.toString().trim()
            val correctCount = count.toString().trim().toInt()
            val shopItem = ShopItem(correctName, correctCount, false)
            val disposable = addShopItemUseCase.addShopItem(shopItem)
                .subscribeOn(Schedulers.io())
                .subscribe({
                }, {
                    Log.i("Here is exception", it.toString())
                })
            _shouldCloseScreen.value = true
            compositeDisposable.add(disposable)
        }
    }

    fun editShopItem(id : Int, name : Editable, count : Editable, isChecked : Boolean) {
        if (checkNameIsCorrect(name) && checkCountIsCorrect(count)) {
            val correctName = name.toString().trim()
            val correctCount = count.toString().trim().toInt()
            val disposable = editShopListUseCase.editShopItem(id, correctName, correctCount, isChecked)
                .subscribeOn(Schedulers.io())
                .subscribe({
                }, {
                    Log.i("Here is exception", it.toString())
                })
            _shouldCloseScreen.value = true
            compositeDisposable.add(disposable)
        }
    }
    private fun checkNameIsCorrect(name : Editable) : Boolean {
        return if(name.toString().trim() != "") {
            true
        } else {
            _errorInputName.value = true
            false
        }
    }
    private fun checkCountIsCorrect(count : Editable) : Boolean {
        return if(count.toString().trim() != "" && count.toString().trim().toInt() > 0) {
            true
        } else {
            _errorInputCount.value = true
            false
        }
    }
    fun textIsWriting() {
        _errorInputCount.value = false
        _errorInputName.value = false
    }
}