package com.kodexgroup.betonapp.screens.productlist

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.screens.app.main.MainAppViewModel
import com.kodexgroup.betonapp.screens.home.HomeFragment
import com.kodexgroup.betonapp.utils.FilterCode
import com.kodexgroup.betonapp.utils.adapters.FactoryListAdapter
import com.kodexgroup.betonapp.utils.adapters.ProductListAdapter
import com.kodexgroup.betonapp.utils.app
import com.kodexgroup.betonapp.utils.findFragment
import com.kodexgroup.betonapp.utils.views.FactoryListView
import com.kodexgroup.betonapp.utils.views.ProductListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class ProductListFragmentController(private val fragment: Fragment, context: Context, view: View) {

    private val isChosen: Boolean = fragment.arguments?.getBoolean("isChosen", false) ?: false

    private val products: ProductListView = view.findViewById(R.id.product_list_view)
    private val factories: FactoryListView = view.findViewById(R.id.factory_list_view)
    private val types: LinearLayout = view.findViewById(R.id.types_card)

    private val typeProduct: LinearLayout = view.findViewById(R.id.types_product)

    private val productShow: TextView = view.findViewById(R.id.choose_product)
    private val factoryShow: TextView = view.findViewById(R.id.choose_factory)

    private val nested: NestedScrollView = view.findViewById(R.id.scroll)
    private val adapter =  ProductListAdapter()

    private val viewModel: ProductListViewModel = ViewModelProvider(fragment).get(ProductListViewModel::class.java)

    private var listProduct: List<Product>? = viewModel.products
    private var listFactory: List<Factory>? = viewModel.factories
    private var listFavorites: MutableList<String>? = null
    private var listFavoritesFactory: MutableList<String>? = null

    init {
        if (isChosen) {
            val favorites = fragment.app.currentUser?.favorites

            val productsList = favorites?.get("products").toString()
            listFavorites = productsList.split(",").toMutableList()
            listFavorites?.remove("")

            val factoryList = favorites?.get("factories").toString()
            listFavoritesFactory = factoryList.split(",").toMutableList()
            listFavoritesFactory?.remove("")
        }

        products.emptyView.setOnActionListener {
            CoroutineScope(Dispatchers.IO).launch {
                reloadList()
            }
        }

        factories.emptyView.setOnActionListener {
            CoroutineScope(Dispatchers.IO).launch {
                reloadListFactory()
            }
        }

        productShow.setOnClickListener {
            factoryShow.setBackgroundResource(R.drawable.ic_button_right_normal)
            productShow.setBackgroundResource(R.drawable.ic_button_left_selected)

            typeProduct.visibility = View.VISIBLE

            products.visibility = View.VISIBLE
            factories.visibility = View.GONE

            CoroutineScope(Dispatchers.IO).launch {
                reloadList()
            }
        }
        factoryShow.setOnClickListener {
            productShow.setBackgroundResource(R.drawable.ic_button_left_normal)
            factoryShow.setBackgroundResource(R.drawable.ic_button_right_selected)

            typeProduct.visibility = View.GONE

            factories.visibility = View.VISIBLE
            products.visibility = View.GONE

            CoroutineScope(Dispatchers.IO).launch {
                reloadListFactory()
            }
        }

        products.listProduct.layoutManager = LinearLayoutManager(context)
        products.listProduct.adapter = adapter

        if (isChosen) {
            types.visibility = View.VISIBLE
        }

        getList()
    }

    private suspend fun load() {
        val types = fragment.arguments?.getIntegerArrayList("type")
        val factory = fragment.arguments?.getString("factoryId")
        var factoryList: List<String>? = null

        if (isChosen && fragment.app.currentUser == null) {
            withContext(Dispatchers.Main) {
                products.userNone()

                products.emptyView.setOnActionListener {
                    val home = findFragment<HomeFragment>(fragment)
                    home?.toProfile()
                }
            }

            return
        }

        if (isChosen && listFavorites.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                products.isEmpty = true
                products.emptyView.setOnActionListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        reloadList()
                    }
                }
            }

            return
        }

        if (factory != null) {
            factoryList = listOf(factory)
        }

        withContext(Dispatchers.Main) {
            products.isLoad = true
        }

        try {
            val productDAO = ServerController().productDAO
            val list = productDAO.getProducts(id = listFavorites, type = types, factory = factoryList)

            listProduct = filtersList(list)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                products.emptyInternetLost()
            }
        }
    }

    private fun filtersList(list: List<Product>) : List<Product> {
        var newList: List<Product> = list

        val filters = fragment.arguments?.getIntegerArrayList("filter")
        filters?.apply {
            for (filter in this) {

                when (filter) {
                    FilterCode.NEAR -> {

                    }
                    FilterCode.SALE -> {
                        newList = newList.filter { it.productSale != 0 }
                    }
                    FilterCode.NEW -> {

                    }

                    FilterCode.RATING_2 -> {

                    }
                    FilterCode.RATING_3-> {

                    }
                    FilterCode.RATING_4 -> {

                    }
                    FilterCode.RATING_5 -> {

                    }
                }

            }
        }

        return newList
    }

    private suspend fun setList() {
        withContext(Dispatchers.Main) {
            listProduct?.apply {
                viewModel.products = this

                if (isNotEmpty()) {
                    showList(this)
                } else {
                    products.isEmpty = true
                }
            }
        }
    }

    private suspend fun loadFactory() {
        withContext(Dispatchers.Main) {
            factories.isLoad = true
        }

        if (isChosen && fragment.app.currentUser == null) {
            withContext(Dispatchers.Main) {
                factories.userNone()

                factories.emptyView.setOnActionListener {
                    val home = findFragment<HomeFragment>(fragment)
                    home?.toProfile()
                }
            }

            return
        }

        if (isChosen && listFavoritesFactory.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                factories.isEmpty = true
                factories.emptyView.setOnActionListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        reloadListFactory()
                    }
                }
            }

            return
        }

        try {
            val factoryDAO = ServerController().factoryDAO
            val list = factoryDAO.getFactory(id = listFavoritesFactory)

            listFactory = list
        } catch (e: Exception) {
            factories.emptyInternetLost()
        }

    }

    private suspend fun setListFactory() {
        withContext(Dispatchers.Main) {
            listFactory?.apply {
                viewModel.factories = this

                if (isNotEmpty()) {
                    factories.showList(reversed())
                } else {
                    factories.isEmpty = true
                }
            }
        }
    }

    private fun showList(list: List<Product>) {
        val newList = mutableListOf<Pair<Product, Product?>>()

        val iterator = list.iterator()
        iterator.forEach { product ->
            val next = if (iterator.hasNext()) iterator.next() else null
            val pair = Pair(product, next)

            newList.add(pair)
        }
        println(newList)
        adapter.reload(newList)

        products.isLoad = false
    }

    private fun getList() {
        CoroutineScope(Dispatchers.IO).launch {
            if (listProduct == null) load()
            setList()
            restoreState()
        }
    }

    suspend fun reloadList() {
        load()
        setList()
    }

    private suspend fun reloadListFactory() {
        loadFactory()
        setListFactory()
    }

    fun saveState() {
        viewModel.listState = nested.scrollY
    }

    private suspend fun restoreState() {
        withContext(Dispatchers.Main) {
            nested.scrollY = viewModel.listState
            viewModel.listState = 0
        }
    }

}