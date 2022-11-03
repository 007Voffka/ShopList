package com.example.shoplist.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.domain.DeleteShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopListUseCase
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase : GetShopListUseCase,
    private val deleteShopListUseCase : DeleteShopItemUseCase,
    private val editShopListUseCase : EditShopItemUseCase
) : ViewModel() {

    fun getShopList() : LiveData<List<ShopItem>> {
        return getShopListUseCase.getShopList()
    }

    fun editShopItem(id : Int, name : String, count : Int, isChecked : Boolean) {
        viewModelScope.launch {
            editShopListUseCase.editShopItem(id, name, count, isChecked)
        }
    }

    fun deleteShopItem(itemId : Int) {
        viewModelScope.launch {
            deleteShopListUseCase.deleteShopItem(itemId)
        }
    }
}