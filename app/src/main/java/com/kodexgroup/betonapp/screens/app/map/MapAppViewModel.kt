package com.kodexgroup.betonapp.screens.app.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kodexgroup.betonapp.database.server.entities.Factory

class MapAppViewModel : ViewModel() {

    private var _factories: MutableLiveData<List<Factory>> = MutableLiveData()
    var state: Int = 0

    var factories: List<Factory>? = null
        get() = _factories.value
        set(value) {
            field = value

            _factories.value = value
        }

}