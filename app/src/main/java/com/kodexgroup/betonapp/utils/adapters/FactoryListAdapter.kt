package com.kodexgroup.betonapp.utils.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.utils.holders.FactoryListHolder
import com.kodexgroup.betonapp.utils.views.FactoryCardView

class FactoryListAdapter(private val context: Context) : RecyclerView.Adapter<FactoryListHolder>() {
    private var list: List<Factory> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactoryListHolder {
        return FactoryListHolder(
                FactoryCardView(context, null))
    }

    override fun onBindViewHolder(holder: FactoryListHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun reload(list: List<Factory>) {
        this.list = list
        notifyDataSetChanged()
    }
}