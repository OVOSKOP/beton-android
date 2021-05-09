package com.kodexgroup.betonapp.screens.factory

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.screens.product.ProductViewModel
import com.kodexgroup.betonapp.utils.app
import com.kodexgroup.betonapp.utils.views.MiniCardProductView
import com.kodexgroup.betonapp.utils.views.RatingView
import com.kodexgroup.betonapp.utils.views.ReviewBlockView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FactoryFragmentController(private val fragment: Fragment, private val context: Context, view: View, factoryId: String) {

    private val previewProduct: ImageView = view.findViewById(R.id.image_main_factory)
    private val title: TextView = view.findViewById(R.id.title_factory)
    private val type: TextView = view.findViewById(R.id.factory_type)
    private val factoryAddressTxt: TextView = view.findViewById(R.id.factory_address)
    private val secondAddress: TextView = view.findViewById(R.id.factory_address_second)
    private val ratingView: RatingView = view.findViewById(R.id.rating_factory)
    private val noteBtn: ImageButton = view.findViewById(R.id.add_favorite_factory)
    private val favoriteCountTxt: TextView = view.findViewById(R.id.favorite_count_factory)

    private val cardFirst: MiniCardProductView = view.findViewById(R.id.first_card)
    private val cardSecond: MiniCardProductView = view.findViewById(R.id.second_card)

    private val reviewBlock: ReviewBlockView = view.findViewById(R.id.review_factory)

    private val emptyProducts: TextView = view.findViewById(R.id.empty_products)
    private val openAllProducts: TextView = view.findViewById(R.id.open_all_products)
    private val allProduct: View = view.findViewById(R.id.all_product)

    private val viewModel: FactoryViewModel = ViewModelProvider(fragment).get(FactoryViewModel::class.java)

    private var factory: Factory? = viewModel.factory
    private var products: List<Product?>? = viewModel.products
    private var isFavorite = false

    init {
        openAllProducts.setOnClickListener {
            val args = Bundle()
            args.putString("factoryId", factoryId)
            args.putString("factory", factory?.factoryName)

            fragment.findNavController().navigate(R.id.to_product_list, args)
        }

        CoroutineScope(Dispatchers.IO).launch {
            if (factory == null) getFactory(factoryId)
            if (products == null) getProducts(factoryId)
            setFactory()
        }
    }

    private suspend fun getFactory(id: String) {
        val dao = ServerController().factoryDAO

        val factories = dao.getFactory(id = listOf(id))
        if (factories.isNotEmpty()) {
            factory = factories.first()
        }
    }

    private suspend fun getProducts(id: String) {
        val dao = ServerController().productDAO

        val productsArr = dao.getProducts(factory = listOf(id))
        if (productsArr.isNotEmpty()) {

            products = productsArr
        }
    }

    private suspend fun setFactory() {
        withContext(Dispatchers.Main) {

            factory?.apply {
                viewModel.factory = this
                previewProduct.setImageResource(R.drawable.concrete)

                title.text = factoryName
                title.background = null
                var param = title.layoutParams
                param.width = ViewGroup.LayoutParams.WRAP_CONTENT

                title.layoutParams = param

                type.text = "Бетонный завод"
                type.background = null
                param = type.layoutParams
                param.width = ViewGroup.LayoutParams.WRAP_CONTENT

                type.layoutParams = param

                factoryAddressTxt.text = factoryAddress
                factoryAddressTxt.background = null
                param = factoryAddressTxt.layoutParams
                param.width = ViewGroup.LayoutParams.WRAP_CONTENT
                factoryAddressTxt.layoutParams = param

                secondAddress.text = factoryAddress
                secondAddress.background = null
                param = secondAddress.layoutParams
                param.width = ViewGroup.LayoutParams.WRAP_CONTENT
                secondAddress.layoutParams = param

                ratingView.rating = factoryRate.toFloat()
                favoriteCountTxt.text = favoriteCount.toString()

                val favorites = fragment.app.currentUser?.favorites
                val factoriesList = favorites?.get("factories") as String
                val listFavorites = factoriesList.split(",").toMutableList()

                if (factoryId in listFavorites) {
                    isFavorite = true
                    noteBtn.setImageResource(R.drawable.ic_bookmark)
                }


                noteBtn.setOnClickListener {
                    if (!isFavorite) {
                        CoroutineScope(Dispatchers.IO).launch {
                            listFavorites.remove("")
                            listFavorites.add(factoryId)

                            favorites.put("factories", listFavorites.joinToString(","))

                            val dao = ServerController().userDAO
                            dao.modifyFavorite(fragment.app.currentUser!!.id, favorites)
                            ServerController().factoryDAO.modify(factoryId, favoriteCount + 1)
                            fragment.app.currentUser!!.favorites = favorites

                            favoriteCount += 1
                            withContext(Dispatchers.Main) {
                                favoriteCountTxt.text = favoriteCount.toString()
                                noteBtn.setImageResource(R.drawable.ic_bookmark)
                            }
                        }

                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            listFavorites.remove("")
                            listFavorites.remove(factoryId)

                            favorites.put("factories", listFavorites.joinToString(","))

                            val dao = ServerController().userDAO
                            dao.modifyFavorite(fragment.app.currentUser!!.id, favorites)
                            ServerController().factoryDAO.modify(factoryId, favoriteCount - 1)
                            fragment.app.currentUser!!.favorites = favorites

                            favoriteCount -= 1
                            withContext(Dispatchers.Main) {
                                favoriteCountTxt.text = favoriteCount.toString()
                                noteBtn.setImageResource(R.drawable.ic_bookmark_empty)
                            }
                        }

                    }

                    isFavorite = !isFavorite
                }


                reviewBlock.addReviews(listOf(), factoryRate.toFloat())

                println(products)

                if (products == null) {

                    allProduct.visibility = View.GONE
                    openAllProducts.visibility = View.GONE

                    emptyProducts.visibility = View.VISIBLE
                } else {
                    val prod = mutableListOf<Product?>()

                    prod.add(products!!.last())
                    prod.add(products!!.elementAtOrNull(products!!.lastIndex - 1))

                    cardFirst.addProduct(prod[0])
                    cardSecond.addProduct(prod[1])

                    if (products!!.size > 2) {
                        openAllProducts.visibility = View.VISIBLE
                    }
                }
            }

        }
    }
}