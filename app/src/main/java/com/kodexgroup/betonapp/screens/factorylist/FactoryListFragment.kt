package com.kodexgroup.betonapp.screens.factorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kodexgroup.betonapp.R

class FactoryListFragment : Fragment() {

    private var controller: FactoryListFragmentController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.content_factory_list, container, false)
        controller = FactoryListFragmentController(this, requireContext(), root)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        controller?.saveState()
    }

}