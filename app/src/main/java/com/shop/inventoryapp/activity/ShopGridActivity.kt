package com.shop.inventoryapp.activity


import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.shop.inventoryapp.Product
import com.example.inventoryapp.R
import com.shop.inventoryapp.adapter.ShopGridAdapter
import com.google.firebase.database.*

class ShopGridActivity : AppCompatActivity(){

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var mFirebaseDatabase: DatabaseReference
    private var shopMap: HashMap<String, Product> = hashMapOf()
    private lateinit var gridView: GridView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseDatabase = firebaseDatabase.reference.child("Shops")

        gridView = findViewById(R.id.grid_view)

        initializedGridView()

    }
    private fun initializedGridView() {
        val adapter = ShopGridAdapter(this, shopMap)
        gridView.adapter = adapter

        mFirebaseDatabase.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val shop = dataSnapshot.getValue(Product::class.java)
                shopMap.put(shop?.proName!!, shop)
                adapter.setData(shopMap)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val shop = dataSnapshot.getValue(Product::class.java)!!
                val shopKey = dataSnapshot.key
                shopMap?.put(shopKey!!, shop)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val shop = dataSnapshot.getValue(Product::class.java)
                shopMap?.remove(shop?.proName, shop)
                adapter.notifyDataSetChanged()
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }
}