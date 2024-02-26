package com.example.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val _errorInputName = MutableLiveData<Boolean>()// работаем с этой из VM
    val errorInputName: LiveData<Boolean>  // работаем с этой из Activity
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()// работаем с этой из VM
    val errorInputCount: LiveData<Boolean>  // работаем с этой из Activity
        get() = _errorInputCount

    private val _shopItemLD = MutableLiveData<ShopItem>()
    val shopItemLD: LiveData<ShopItem>
        get() = _shopItemLD

    private val _finishCurrentScreenLD = MutableLiveData<Unit>()
    val finishCurrentScreenLD: LiveData<Unit>
        get() = _finishCurrentScreenLD

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    fun getShopItem(shopItemID: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemID)
        _shopItemLD.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String) {
        val name = parseString(inputName)
        val count = parseCount(inputCount)
        val validationResult = validateInputTypes(name, count)
        if (validationResult) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            _finishCurrentScreenLD.value = Unit
        }
    }

    fun editShopItem(inputName: String?, inputCount: String) {
        val name = parseString(inputName)
        val count = parseCount(inputCount)
        val validationResult = validateInputTypes(name, count)
        if (validationResult) {
            _shopItemLD.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                _finishCurrentScreenLD.value = Unit
            }
        }
    }

    private fun parseString(shopItemName: String?): String {
        return shopItemName?.trim() ?: ""
    }

    private fun parseCount(shopItemCount: String?): Int {
        return try {
            shopItemCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInputTypes(inputString: String, inputCount: Int): Boolean {
        var result = true
        if (inputCount <= 0) {
            _errorInputCount.value = true
            result = false
        }
        if (inputString.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }
}