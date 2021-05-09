package com.kodexgroup.betonapp.screens.product

import androidx.fragment.app.Fragment
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.utils.app
import com.kodexgroup.betonapp.utils.views.MiniCardProductView
import com.kodexgroup.betonapp.utils.views.RatingView
import com.kodexgroup.betonapp.utils.views.ReviewBlockView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductFragmentController(private val fragment: Fragment, private val context: Context, view: View, productId: String) {

    private val previewProduct: ImageView = view.findViewById(R.id.image_main_product)
    private val title: TextView = view.findViewById(R.id.title_product)
    private val factoryBlock: LinearLayout = view.findViewById(R.id.factory_block)
    private val titleFactory: TextView = view.findViewById(R.id.factory_title_product)
    private val factoryBtn: TextView = view.findViewById(R.id.to_factory_form_product)
    private val ratingView: RatingView = view.findViewById(R.id.rating_product)
    private val noteBtn: ImageButton = view.findViewById(R.id.add_favorite_product)
    private val favoriteCountTxt: TextView = view.findViewById(R.id.favorite_count_product)
    private val description: TextView = view.findViewById(R.id.description_product)
    private val priceBlock: LinearLayout = view.findViewById(R.id.price_block)
    private val price: TextView = view.findViewById(R.id.price_main_product)
    private val salePrice: TextView = view.findViewById(R.id.price_sale_product)
    private val addToBucket: Button = view.findViewById(R.id.to_busket_product)
    private val reviewBlock: ReviewBlockView = view.findViewById(R.id.reviews_product)
    private val cardFirst: MiniCardProductView = view.findViewById(R.id.first_card)
    private val cardSecond: MiniCardProductView = view.findViewById(R.id.second_card)

    private val titleLiked: TextView = view.findViewById(R.id.title_liked)
    private val likedBlock: View = view.findViewById(R.id.liked_block)

    private val viewModel: ProductViewModel = ViewModelProvider(fragment).get(ProductViewModel::class.java)

    private var product: Product? = viewModel.product
    private var factory: Factory? = viewModel.factory
    private var isFavorite = false

    init {
        println(product)
        CoroutineScope(Dispatchers.IO).launch {
            if (product == null) getProduct(productId)
            setProduct()
            product?.apply {
                getFactory(factory)
                getLiked(productType)
            }
        }
    }

    private suspend fun getProduct(id: String) {
        val dao = ServerController().productDAO

        val products = dao.getProducts(listOf(id))
        if (products.isNotEmpty()) {
            product = products.first()
        }
    }

    private suspend fun setProduct() {
        withContext(Dispatchers.Main) {

            product?.apply {
                viewModel.product = this

                previewProduct.setImageResource(R.drawable.concrete)

                title.text = productName
                title.background = null
                val param = title.layoutParams
                param.width = ViewGroup.LayoutParams.WRAP_CONTENT

                title.layoutParams = param

                ratingView.rating = productRate.toFloat()
                favoriteCountTxt.text = favoriteCount.toString()

                val favorites = fragment.app.currentUser?.favorites
                val products = favorites?.get("products") as String
                val listFavorites = products.split(",").toMutableList()

                if (productId in listFavorites) {
                    isFavorite = true
                    noteBtn.setImageResource(R.drawable.ic_bookmark)
                }

                noteBtn.setOnClickListener {
                    if (!isFavorite) {
                        CoroutineScope(Dispatchers.IO).launch {
                            listFavorites.remove("")
                            listFavorites.add(productId)

                            favorites.put("products", listFavorites.joinToString(","))

                            val dao = ServerController().userDAO
                            dao.modifyFavorite(fragment.app.currentUser!!.id, favorites)
                            ServerController().productDAO.modify(productId, favoriteCount + 1)
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
                            listFavorites.remove(productId)

                            favorites.put("products", listFavorites.joinToString(","))

                            val dao = ServerController().userDAO
                            dao.modifyFavorite(fragment.app.currentUser!!.id, favorites)
                            ServerController().productDAO.modify(productId, favoriteCount - 1)
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

                description.text = productDescription
                description.background = null

                var priceTotal = productPrice
                if (productSale != 0) {
                    salePrice.text = context.getString(R.string.sale_price_strike, priceTotal)
                    salePrice.paintFlags = salePrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    salePrice.visibility = View.VISIBLE

                    priceTotal -= (priceTotal * productSale / 100)
                    price.text = context.getString(R.string.price_mini_card, priceTotal)
                } else {
                    price.text = context.getString(R.string.price_mini_card, priceTotal)
                }
                priceBlock.background = null
                price.visibility = View.VISIBLE
                addToBucket.visibility = View.VISIBLE

                reviewBlock.addReviews(listOf(), productRate.toFloat())

            }

        }
    }

    private suspend fun getFactory(id: String) {
        if (factory == null) {
            val dao = ServerController().factoryDAO

            val factories = dao.getFactory(id = listOf(id))
            if (factories.isNotEmpty()) {
                factory = factories.first()
            }
        }

        setFactory()
    }

    private suspend fun setFactory() {
        withContext(Dispatchers.Main) {

            factory?.apply {
                viewModel.factory = this
                titleFactory.text = factoryName

                factoryBlock.background = null
                titleFactory.visibility = View.VISIBLE
                factoryBtn.visibility = View.VISIBLE

                factoryBtn.setOnClickListener {
                    val args = Bundle()
                    args.putString("factoryId", factoryId)

                    fragment.findNavController().navigate(R.id.to_factory, args)
                }
            }

        }
    }

    private suspend fun getLiked(productType: Int) {
        val dao = ServerController().productDAO

        var products: List<Product?>
        if (viewModel.liked == null) {
            products = dao.getProducts(type = listOf(productType))
            products = products.filter { it.productId != product?.productId }
        } else {
            products = viewModel.liked!!
        }

        withContext(Dispatchers.Main) {
            if (products.isNotEmpty()) {
                val list = listOf(products[0], products.getOrNull(1))
                viewModel.liked = list

                cardFirst.addProduct(list[0])
                cardSecond.addProduct(list[1])
            } else {
                titleLiked.visibility = View.GONE
                likedBlock.visibility = View.GONE
            }
        }
    }

}