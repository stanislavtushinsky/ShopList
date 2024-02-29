package com.example.shoplist.presentation

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.DeleteShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopListUseCase
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val editShopItemUseCase = EditShopItemUseCase(repository);
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository);
    private val getShopListUseCase = GetShopListUseCase(repository);

    val shopListLD = getShopListUseCase.getShopList() //автообновление LD
    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
            Log.d(
                ContentValues.TAG,
                "deleteShopItem MainVM from VMScope.launch() ${viewModelScope.coroutineContext} "
            )
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newItem)
            Log.d(
                ContentValues.TAG,
                "changeEnableState MainVM from VMScope.launch() ${viewModelScope.coroutineContext} "
            )
        }
    }
}