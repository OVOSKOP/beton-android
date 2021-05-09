package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.utils.FilterCode
import com.kodexgroup.betonapp.utils.findParentNavController

class MainMenuView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val products: ButtonMenuView
    private val sales: ButtonMenuView
    private val newest: ButtonMenuView
    private val favorite: ButtonMenuView

    private var navController: NavController? = null

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_main_menu, this, true)

        products = root.findViewById(R.id.products_open)
        sales = root.findViewById(R.id.sale_open)
        newest = root.findViewById(R.id.newest_open)
        favorite = root.findViewById(R.id.favorites_open)

        val args = Bundle()

        products.setOnClickListener {
            navController?.navigate(R.id.to_product_list)
        }

        sales.setOnClickListener {
            args.putIntegerArrayList("filter", arrayListOf(FilterCode.SALE))
            navController?.navigate(R.id.to_product_list, args)
        }

        newest.setOnClickListener {
            args.putIntegerArrayList("filter", arrayListOf(FilterCode.NEW))
            navController?.navigate(R.id.to_product_list, args)
        }

        favorite.setOnClickListener {
            args.putBoolean("isChosen", true)
            navController?.navigate(R.id.to_product_list, args)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (!isInEditMode) {
            navController = findNavController()
        }
    }

}