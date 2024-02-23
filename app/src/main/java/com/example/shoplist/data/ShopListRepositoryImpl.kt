package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository

class ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    } 

    override fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("ShopItem with id $shopItemId not found")
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }
}