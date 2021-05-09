package com.kodexgroup.betonapp.utils.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.utils.holders.ProductListHolder
import com.kodexgroup.betonapp.utils.views.MiniCardProductView

class ProductListAdapter : RecyclerView.Adapter<ProductListHolder>() {
    private var list: List<Pair<Product, Product?>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        return ProductListHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.view_pair_mini_card, parent, false))
    }

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun reload(list: List<Pair<Product, Product?>>) {
        this.list = list
        notifyDataSetChanged()
    }
}