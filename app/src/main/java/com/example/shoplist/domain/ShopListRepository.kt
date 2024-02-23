package com.example.shoplist.domain

interface ShopListRepository {
    fun getShopList():List<ShopItem>
    fun addShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItem(shopItemId: Int): ShopItem
    fun deleteShopItem(shopItem: ShopItem)
}