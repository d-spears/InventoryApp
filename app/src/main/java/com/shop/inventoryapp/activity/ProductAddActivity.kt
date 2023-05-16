package com.shop.inventoryapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.shop.inventoryapp.Product
import com.example.inventoryapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProductAddActivity : AppCompatActivity() {

    private var mFirebaseDBInstance: FirebaseDatabase? = null
    private var mFirebaseDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_add_layout)

        mFirebaseDBInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseDBInstance?.getReference("Shops")

        val IDGiven: EditText = findViewById(R.id.shop_id)
        val nameGiven: EditText = findViewById(R.id.shop_name)
        val descGiven: EditText = findViewById(R.id.shop_desc)
        val imageGiven: EditText = findViewById(R.id.shop_url)

        val addBtn: Button = findViewById(R.id.shop_add_button)

        addBtn.setOnClickListener{
            val shop = Product(IDGiven.text.toString(), nameGiven.text.toString(), descGiven.text.toString(), imageGiven.text.toString())

            mFirebaseDatabase!!.child(IDGiven.text.toString()).setValue(shop)


            finish()

        }
    }
}