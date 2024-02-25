package com.example.shoplist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.shoplist.R

class ShopItemActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
    }
}