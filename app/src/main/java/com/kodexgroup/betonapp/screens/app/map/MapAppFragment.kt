package com.kodexgroup.betonapp.screens.app.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.kodexgroup.betonapp.R

class MapAppFragment : Fragment(), OnMapReadyCallback {

    private lateinit var controller: MapAppFragmentController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.content_map, container, false)

        controller = MapAppFragmentController(this, requireContext(), root)

        val mapFragment =  childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return root
    }

    override fun onMapReady(map: GoogleMap) {
        controller.map = map
    }

}