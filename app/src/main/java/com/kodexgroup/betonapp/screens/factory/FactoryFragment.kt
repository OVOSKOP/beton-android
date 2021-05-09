package com.kodexgroup.betonapp.screens.factory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kodexgroup.betonapp.R

class FactoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.content_factory, container, false)

        val id = arguments?.getString("factoryId") ?: ""
        FactoryFragmentController(this, requireContext(), root, id)

        return root
    }

}