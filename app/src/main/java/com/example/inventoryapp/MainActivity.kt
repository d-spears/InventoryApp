package com.example.inventoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.GridView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarToggle: ActionBarDrawerToggle
    lateinit var navigation_view: NavigationView
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var mFirebaseDatabase: DatabaseReference
    private var shopMap: HashMap<String, Product> = hashMapOf()
    private lateinit var gridView: GridView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer)

        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        firebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseDatabase = firebaseDatabase.getReference("products")

        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        navigation_view = findViewById(R.id.nav_view)
        navigation_view.setNavigationItemSelectedListener{menuItem ->
            when(menuItem.itemId){
                R.id.add_product -> {
                    val intent = Intent(this, ProductAddActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.shop -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }


        gridView = findViewById(R.id.grid_view)
    }

    // toggle bar won't open and close without this function
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        showGridLayout()
    }

    fun showGridLayout(){
        firebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseDatabase = firebaseDatabase.getReference("Shops")

        val adapter = ShopGridAdapter(this, shopMap)
        gridView.adapter = adapter
        adapter.setData(shopMap)

        gridView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ShopItemActivity::class.java)
            intent.putExtra("id", shopMap.get(shopMap.keys.elementAt(position))?.proID)
            startActivity(intent)
        }

        mFirebaseDatabase.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val shop = dataSnapshot.getValue(Product::class.java)
                shopMap.put(shop?.proID!!, shop)
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
                val key = dataSnapshot.key
                shopMap.remove(key)
                mFirebaseDatabase.child(key!!).removeValue()
                adapter.notifyDataSetChanged()
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}