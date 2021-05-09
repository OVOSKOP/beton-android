package com.kodexgroup.betonapp.utils.holders

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.utils.views.MiniCardProductView

class ProductListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val first = itemView.findViewById<MiniCardProductView>(R.id.first_card)
    private val second = itemView.findViewById<MiniCardProductView>(R.id.second_card)

    fun onBind(items: Pair<Product, Product?>) {
        first.addProduct(items.first)
        if (items.second != null) {
            second.visibility = View.VISIBLE
            second.addProduct(items.second!!)
        } else {
            second.visibility = View.INVISIBLE
        }
    }
}