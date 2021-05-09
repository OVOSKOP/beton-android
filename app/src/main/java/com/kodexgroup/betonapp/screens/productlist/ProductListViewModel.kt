package com.kodexgroup.betonapp.screens.productlist

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.utils.adapters.ProductListAdapter

class ProductListViewModel : ViewModel() {

    private var _products: MutableLiveData<List<Product>> = MutableLiveData()
    private var _factories: MutableLiveData<List<Factory>> = MutableLiveData()
    var listState: Int = 0

    var products: List<Product>? = null
        get() = _products.value
        set(value) {
            field = value

            _products.value = value
        }

    var factories: List<Factory>? = null
        get() = _factories.value
        set(value) {
            field = value

            _factories.value = value
        }

}