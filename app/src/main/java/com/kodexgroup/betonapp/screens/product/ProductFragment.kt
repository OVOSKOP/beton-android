package com.kodexgroup.betonapp.screens.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kodexgroup.betonapp.R

class ProductFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.content_product, container, false)

        val id = arguments?.getString("productId") ?: ""

        ProductFragmentController(this, requireContext(), root, id)

        return root
    }

}