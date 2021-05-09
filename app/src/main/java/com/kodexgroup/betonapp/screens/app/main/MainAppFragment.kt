package com.kodexgroup.betonapp.screens.app.main

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kodexgroup.betonapp.R

class MainAppFragment : Fragment() {

    private var controller: MainAppFragmentController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.content_home, container, false)

        controller = MainAppFragmentController(this, requireContext(), root)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        controller?.saveState()
    }

}