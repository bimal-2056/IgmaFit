package com.example.rese_yl.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rese_yl.R
import com.example.rese_yl.models.Product
import com.example.rese_yl.ui.activities.ProductDetailsActivity
import com.example.rese_yl.utils.Constants
import kotlin.random.Random

class DashboardItemsPagerAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<DashboardItemsPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_slideshow_image)

        init {
            itemView.setOnClickListener {
                val product = products[adapterPosition]

                val intent = Intent(itemView.context, ProductDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
                intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, product.user_id)

                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.slideshow_item, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        Glide.with(holder.itemView.context)
            .load(product.image)
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount() = products.size
}
