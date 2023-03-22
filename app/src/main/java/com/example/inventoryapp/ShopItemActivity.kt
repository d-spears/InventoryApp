package com.example.inventoryapp


import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class ShopItemActivity : AppCompatActivity() {

    private lateinit var shopImageDetails: ImageView
    private lateinit var shopNameView: TextView
    private lateinit var shopID: TextView
    private lateinit var shopDesc: TextView
    private lateinit var productID: HashMap<String, Product>

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var mFirebaseDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_item_layout)

        firebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseDatabase = firebaseDatabase.getReference("Shops")

        shopImageDetails = findViewById(R.id.shop_image_view)
        shopNameView = findViewById(R.id.shop_name_view)
        shopID = findViewById(R.id.shop_id_view)
        shopDesc = findViewById(R.id.shop_desc_view)

        val id = intent.getStringExtra("id")!!
        mFirebaseDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val shop = snapshot.child(id).getValue(Product::class.java)
                shopImageDetails = findViewById(R.id.shop_image_view)
                shopNameView = findViewById(R.id.shop_name_view)
                shopID = findViewById(R.id.shop_id_view)
                shopDesc = findViewById(R.id.shop_desc_view)

                shopNameView.text = shop?.proName
                shopID.text = "ID: ${shop?.proID}"
                shopNameView.text = "Name: ${shop?.proName}"
                shopDesc.text = "Description: ${shop?.proDesc}"

                loadImage(shop?.proImage)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun loadImage(url: String?){
        Glide.with(this)
            .load(url)
            .into(shopImageDetails)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}