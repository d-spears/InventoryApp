package com.shop.inventoryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.inventoryapp.viewmodel.ShopRepository
import com.shop.inventoryapp.Product

class MainViewModel(private val repository: ShopRepository) : ViewModel() {
        val shops: LiveData<List<Product>> = repository.getShops()
    }