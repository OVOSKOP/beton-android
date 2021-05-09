package com.kodexgroup.betonapp.screens.app.map

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.database.server.entities.Factory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapAppFragmentController(fragment: Fragment, private val context: Context, view: View) {

    var map: GoogleMap? = null
    set(value) {
        field = value

        map?.setOnMarkerClickListener {

            true
        }

        CoroutineScope(Dispatchers.IO).launch {
            if (listFactory == null) load()
            setList()
        }
    }

    private val viewModel: MapAppViewModel = ViewModelProvider(fragment).get(MapAppViewModel::class.java)

    private var listFactory: List<Factory>? = viewModel.factories

    init {

    }

    private suspend fun load() {
        try {
            val factoryDAO = ServerController().factoryDAO
            val list = factoryDAO.getFactory()

            listFactory = list
        } catch (e: Exception) {

        }

    }

    private suspend fun setList() {
        withContext(Dispatchers.Main) {
            listFactory?.apply {
                viewModel.factories = this

                if (isNotEmpty()) {
                    setMarkers(this)
                }
            }
        }
    }

    private fun setMarkers(list: List<Factory>) {
        list.forEach { factory ->
            val chords = factory.factoryCoords.split(",").map { it.toDouble() }

            val marker = MarkerOptions()
                .position(LatLng(chords[0], chords[1]))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_factory_mini))
            map?.addMarker(marker)
        }
    }

}