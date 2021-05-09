package com.kodexgroup.betonapp.screens.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.database.server.entities.Product

class ProductViewModel : ViewModel() {

    private var _product: MutableLiveData<Product> = MutableLiveData()
    private var _factory: MutableLiveData<Factory> = MutableLiveData()
    private var _liked: MutableLiveData<List<Product?>> = MutableLiveData()

    var product: Product? = null
        get() = _product.value
        set(value) {
            field = value

            _product.value = value
        }

    var factory: Factory? = null
        get() = _factory.value
        set(value) {
            field = value

            _factory.value = value
        }

    var liked: List<Product?>? = null
        get() = _liked.value
        set(value) {
            field = value

            _liked.value = value
        }

    override fun onCleared() {
        super.onCleared()

        product = null
    }
}