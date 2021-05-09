package com.kodexgroup.betonapp.database.server.entities

import com.kodexgroup.betonapp.utils.json.JSON
import com.kodexgroup.betonapp.utils.xml.interfaces.IXMLClass
import org.json.JSONObject
import java.util.*

class Factory() : IXMLClass {

    private lateinit var _factoryId: String
    var factoryId: String = ""
        get() = _factoryId
        set(value) {
            field = value
            _factoryId = value
        }

    private lateinit var _factoryName: String
    var factoryName: String = ""
        get() = _factoryName
        set(value) {
            field = value
            _factoryName = value
        }

    private lateinit var _factoryAddress: String
    var factoryAddress: String = ""
        get() = _factoryAddress
        set(value) {
            field = value
            _factoryAddress = value
        }

    private lateinit var _factoryCoords: String
    var factoryCoords: String = ""
        get() = _factoryCoords
        set(value) {
            field = value
            _factoryCoords = value
        }

    private var _factoryRate: Double = 0.0
    var factoryRate: Double = 0.0
        get() = _factoryRate
        set(value) {
            field = value
            _factoryRate = value
        }

    private var _ownUserId: String = ""
    var ownUserId: String = ""
        get() = _ownUserId
        set(value) {
            field = value
            _ownUserId = value
        }

    private var _favoriteCount: Int = 0
    var favoriteCount: Int = 0
        get() = _favoriteCount
        set(value) {
            field = value
            _favoriteCount = value
        }

    private var _inn: String = ""
    var inn: String = ""
        get() = _inn
        set(value) {
            field = value
            _inn = value
        }

    private var _ogrn: String = ""
    var ogrn: String = ""
        get() = _ogrn
        set(value) {
            field = value
            _ogrn = value
        }

    private var _kpp: String = ""
    var kpp: String = ""
        get() = _kpp
        set(value) {
            field = value
            _kpp = value
        }

    private lateinit var _factoryPhotos: JSONObject
    var factoryPhotos: JSONObject = JSONObject()
        get() = _factoryPhotos
        set(value) {
            field = value
            _factoryPhotos = value
        }

    private var _factoryCountOrders: Int = 0
    var factoryCountOrders: Int = 0
        get() = _factoryCountOrders
        set(value) {
            field = value
            _factoryCountOrders = value
        }

    private var _factoryAveragePrice: Int = 0
    var factoryAveragePrice: Int = 0
        get() = _factoryAveragePrice
        set(value) {
            field = value
            _factoryAveragePrice = value
        }

    private lateinit var _dateAdded: Date
    var dateAdded: Date = Date()
        get() = _dateAdded
        set(value) {
            field = value
            _dateAdded = value
        }

    private var _isDeleted: Boolean = false
    var isDeleted: Boolean = false
        get() = _isDeleted
        set(value) {
            field = value
            _isDeleted = value
        }

    constructor(factoryId: String,
                factoryName: String,
                factoryAddress: String,
                factoryCoords: String,
                factoryRate: Double,
                ownUserId: String,
                favoriteCount: Int,
                inn: String,
                ogrn: String,
                kpp: String,
                factoryPhotos: JSONObject,
                factoryCountOrders: Int,
                factoryAveragePrice: Int,
                dateAdded: Date,
                isDeleted: Boolean
                ) : this() {
        _factoryId = factoryId
        _factoryName = factoryName
        _factoryAddress = factoryAddress
        _factoryCoords = factoryCoords
        _factoryRate = factoryRate
        _ownUserId = ownUserId
        _favoriteCount = favoriteCount
        _inn = inn
        _ogrn = ogrn
        _kpp = kpp
        _factoryPhotos = factoryPhotos
        _factoryCountOrders = factoryCountOrders
        _factoryAveragePrice = factoryAveragePrice
        _dateAdded = dateAdded
        _isDeleted = isDeleted
    }

    companion object {
        const val START_TAG = "Factory"
        const val ID = "Id"
        const val NAME = "Name"
        const val ADDRESS = "Address"
        const val COORDS = "Coords"
        const val RATE = "Rate"
        const val OWN_USER = "OwnUser"
        const val FAVORITE_COUNT = "FavoritesCount"
        const val INN = "INN"
        const val OGRN = "OGRN"
        const val KPP = "KPP"
        const val PHOTOS = "Photos"
        const val COUNT_ORDERS = "OrdersCount"
        const val AVERAGE_PRICE = "AveragePrice"
        const val DATE = "Date"
        const val DELETED = "Deleted"
    }

    override fun setData(key: String, value: String?) {
        val newValue = value ?: ""

        when (key) {
            ID -> {
                _factoryId = newValue
            }
            NAME -> {
                _factoryName = newValue
            }
            ADDRESS -> {
                _factoryAddress = newValue
            }
            COORDS -> {
                _factoryCoords = newValue
            }
            RATE -> {
                _factoryRate = newValue.toDoubleOrNull() ?: 0.0
            }
            OWN_USER -> {
                _ownUserId = newValue
            }
            FAVORITE_COUNT -> {
                _favoriteCount = newValue.toIntOrNull() ?: 0
            }
            INN -> {
                _inn = newValue
            }
            OGRN -> {
                _ogrn = newValue
            }
            KPP -> {
                _kpp = newValue
            }
            PHOTOS -> {
                _factoryPhotos = JSON.Response(newValue)
            }
            COUNT_ORDERS -> {
                _factoryCountOrders = newValue.toIntOrNull() ?: 0
            }
            AVERAGE_PRICE -> {
                _factoryAveragePrice = newValue.toIntOrNull() ?: 0
            }
            DATE -> {
                _dateAdded = Date(newValue.toLongOrNull() ?: 0)
            }
            DELETED -> {
                _isDeleted = !newValue.contains("0")
            }
        }
    }

}