package com.shop.inventoryapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.shop.inventoryapp.adapter.ShopGridAdapter
import com.example.inventoryapp.databinding.ActivityMainBinding
import com.shop.inventoryapp.factory.MainViewModelFactory
import com.shop.inventoryapp.viewmodel.MainViewModel
import com.example.inventoryapp.viewmodel.ShopRepository

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory(ShopRepository())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.shops.observe(this) { shops ->
            val adapter = ShopGridAdapter(this, hashMapOf())
            binding.gridView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}
