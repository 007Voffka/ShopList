package com.example.shoplist.presentation.view_models

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddItemViewModel @Inject constructor(
    private val editShopListUseCase : EditShopItemUseCase,
    private val addShopItemUseCase : AddShopItemUseCase,
    private val getShopItemUseCase : GetShopItemUseCase
) : ViewModel() {


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName : LiveData<Boolean>
    get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldCloseScreen = MutableLiveData<Boolean>()
    val shouldCloseScreen : LiveData<Boolean>
        get() = _shouldCloseScreen

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem : LiveData<ShopItem>
    get() = _shopItem


    fun getShopItem(id : Int) {
        viewModelScope.launch {
            val shopItem = getShopItemUseCase.getShopItem(id)
            _shopItem.postValue(shopItem)
        }
    }

    fun addShopItem(name : Editable, count : Editable) {
        if (!checkNameIsError(name) && !checkCountIsError(count)) {
            val correctName = name.toString().trim()
            val correctCount = count.toString().trim().toInt()
            val shopItem = ShopItem(correctName, correctCount, false)
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(shopItem)
            }
            _shouldCloseScreen.value = true
        }
    }

    fun editShopItem(id : Int, name : Editable, count : Editable, isChecked : Boolean) {
        if (!checkNameIsError(name) && !checkCountIsError(count)) {
            val correctName = name.toString().trim()
            val correctCount = count.toString().trim().toInt()
            viewModelScope.launch {
                editShopListUseCase.editShopItem(id, correctName, correctCount, isChecked)
            }
            _shouldCloseScreen.value = true
        }
    }
    private fun checkNameIsError(name : Editable) : Boolean {
        return if(name.toString().trim() != "") {
            false
        } else {
            _errorInputName.value = true
             true
        }
    }
    private fun checkCountIsError(count : Editable) : Boolean {
        return if(count.toString().trim() != "" && count.toString().trim().toInt() > 0) {
            false
        } else {
            _errorInputCount.value = true
            true
        }
    }
    fun textIsWriting() {
        _errorInputCount.value = false
        _errorInputName.value = false
    }
}