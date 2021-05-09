package com.kodexgroup.betonapp.utils.holders

import androidx.recyclerview.widget.RecyclerView
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.utils.views.FactoryCardView

class FactoryListHolder(itemView: FactoryCardView) : RecyclerView.ViewHolder(itemView) {

    private val itemCard = itemView

    fun onBind(item: Factory) {
        itemCard.addFactory(item)
    }
}