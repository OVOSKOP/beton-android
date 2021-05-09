package com.kodexgroup.betonapp.screens.factory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.database.server.entities.Product

class FactoryViewModel : ViewModel() {

    private var _factory: MutableLiveData<Factory> = MutableLiveData()
    private var _products: MutableLiveData<List<Product>> = MutableLiveData()

    var factory: Factory? = null
        get() = _factory.value
        set(value) {
            field = value

            _factory.value = value
        }

    var products: List<Product>? = null
        get() = _products.value
        set(value) {
            field = value

            _products.value = value
        }

}