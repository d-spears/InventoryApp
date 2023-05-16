package com.shop.inventoryapp.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.shop.inventoryapp.Product
import com.example.inventoryapp.R
import com.shop.inventoryapp.activity.ShopItemActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
class ShopGridAdapter(val context: Context, var shopList: HashMap<String, Product>): BaseAdapter() {
    private var keys: MutableList<String> = shopList.keys.toMutableList()
    override fun getCount(): Int {
        return shopList.size
    }
    override fun getItem(position: Int): Any {
        return shopList.get(keys.elementAt(position))!!
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        val holder: ViewHolder

        if(itemView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.grid_layout, null)
            holder = ViewHolder(itemView)
            holder.productName = itemView.findViewById(R.id.name_view)
            //holder.productDesc = itemView.findViewById(R.id.desc_view)
            holder.productImage = itemView.findViewById(R.id.image_view)
            holder.removeBtn = itemView.findViewById(R.id.delete_btn)
            itemView.tag = holder
        }
        else {
            holder = itemView.tag as ViewHolder
        }

        val viewItems = shopList.get(keys.elementAt(position))

        holder.productName.text = viewItems?.proName

        if(holder.productImage != null){
            Glide.with(context)
                .load(viewItems?.proImage)
                .into(holder.productImage)
        }

        // Set the click listener for removeBtn
        holder.removeBtn.setOnClickListener {
            val viewHolder = itemView?.tag as ViewHolder
            val position = keys.indexOf(viewItems?.proID)
            shopList.remove(viewItems?.proID)
            keys.removeAt(position)
            notifyDataSetChanged()

            // Remove the item from the Firebase Realtime Database
            val databaseRef = Firebase.database.getReference("Shops").child(viewItems?.proID.toString())
            databaseRef.removeValue()
        }

        // set the listener for each item in the GridView
        itemView?.setOnClickListener {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra("id", viewItems?.proID)
            context.startActivity(intent)
        }

        return itemView!!
    }

    inner class ViewHolder(itemView: View) {
        lateinit var productName: TextView
        //lateinit var productDesc: TextView
        lateinit var productImage: ImageView
        lateinit var removeBtn: Button
    }

    fun setData(data: HashMap<String, Product>) {
        shopList = data
        keys = shopList.keys.toMutableList()
        notifyDataSetChanged()
    }
}