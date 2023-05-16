package com.example.inventoryapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shop.inventoryapp.Product
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ShopRepository {
    private val database = Firebase.database.reference.child("shops")

    fun getShops(): LiveData<List<Product>> {
        val liveData = MutableLiveData<List<Product>>()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val shops = mutableListOf<Product>()
                for (shopSnapshot in snapshot.children) {
                    val shop = shopSnapshot.getValue(Product::class.java)
                    shop?.let { shops.add(it) }
                }
                liveData.value = shops
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        return liveData
    }
}