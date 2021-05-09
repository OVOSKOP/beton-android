package com.kodexgroup.betonapp.database.server.entities

import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.utils.json.JSON
import com.kodexgroup.betonapp.utils.xml.interfaces.IXMLClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Date

class Product() : IXMLClass {

    private lateinit var _productId: String
    var productId: String = ""
        get() = _productId
        set(value) {
            field = value
            _productId = value
        }

    private var _factory: String = ""
    var factory: String = ""
        get() = _factory
        set(value) {
            field = value
            _factory = value
        }

    private lateinit var _productName: String
    var productName: String = ""
        get() = _productName
        set(value) {
            field = value
            _productName = value
        }

    private lateinit var _productDescription: String
    var productDescription: String = ""
        get() = _productDescription
        set(value) {
            field = value
            _productDescription = value
        }

    private var _productRate: Double = 0.0
    var productRate: Double = 0.0
        get() = _productRate
        set(value) {
            field = value
            _productRate = value
        }

    private var _favoriteCount: Int = 0
    var favoriteCount: Int = 0
        get() = _favoriteCount
        set(value) {
            field = value
            _favoriteCount = value
        }

    private var _productPrice: Int = 0
    var productPrice: Int = 0
        get() = _productPrice
        set(value) {
            field = value
            _productPrice = value
        }

    private var _productSale: Int = 0
    var productSale: Int = 0
        get() = _productSale
        set(value) {
            field = value
            _productSale = value
        }

    private lateinit var _productPhotos: JSONObject
    var productPhotos: JSONObject = JSONObject()
        get() = _productPhotos
        set(value) {
            field = value
            _productPhotos = value
        }

    private var _productCountOrders: Int = 0
    var productCountOrders: Int = 0
        get() = _productCountOrders
        set(value) {
            field = value
            _productCountOrders = value
        }

    private var _productType: Int = 0
    var productType: Int = 0
        get() = _productType
        set(value) {
            field = value
            _productType = value
        }

    private lateinit var _dateAdded: Date
    var dateAdded: Date = Date()
        get() = _dateAdded
        set(value) {
            field = value
            _dateAdded = value
        }

    constructor(productId: String,
                factory: String,
                productName: String,
                productDescription: String,
                productRate: Double,
                favoriteCount: Int,
                productPrice: Int,
                productSale: Int,
                productPhotos: JSONObject,
                productCountOrders: Int,
                productType: Int,
                dateAdded: Date) : this() {
        _productId = productId
        _factory = factory
        _productName = productName
        _productDescription = productDescription
        _productRate = productRate
        _favoriteCount = favoriteCount
        _productPrice = productPrice
        _productSale = productSale
        _productPhotos = productPhotos
        _productCountOrders = productCountOrders
        _productType = productType
        _dateAdded = dateAdded
    }

    companion object {
        const val START_TAG = "Product"
        const val ID = "Id"
        const val FACTORY = "Factory"
        const val NAME = "Name"
        const val DESCRIPTION = "Description"
        const val RATE = "Rate"
        const val FAVORITE_COUNT = "FavoriteCount"
        const val PRICE = "Price"
        const val SALE = "Sale"
        const val PHOTOS = "Photos"
        const val COUNT_ORDERS = "CountOrders"
        const val TYPE = "Type"
        const val DATE = "Date"
    }

    override fun setData(key: String, value: String?) {
        val newValue = value ?: ""

        when (key) {
            ID -> {
                _productId = newValue
            }
            FACTORY -> {
                _factory = newValue
            }
            NAME -> {
                _productName = newValue
            }
            DESCRIPTION -> {
                _productDescription = newValue
            }
            RATE -> {
                _productRate = newValue.toDoubleOrNull() ?: 0.0
            }
            FAVORITE_COUNT -> {
                _favoriteCount = newValue.toIntOrNull() ?: 0
            }
            PRICE -> {
                _productPrice = newValue.toIntOrNull() ?: 0
            }
            SALE -> {
                _productSale = newValue.toIntOrNull() ?: 0
            }
            PHOTOS -> {
                _productPhotos = JSON.Response(newValue)
            }
            COUNT_ORDERS -> {
                _productCountOrders = newValue.toIntOrNull() ?: 0
            }
            TYPE -> {
                _productType = newValue.toIntOrNull() ?: 0
            }
            DATE -> {
                _dateAdded = Date(newValue.toLongOrNull() ?: 0)
            }
        }
    }
}