package com.kodexgroup.betonapp.screens.app.main

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.screens.product.ProductViewModel
import com.kodexgroup.betonapp.utils.views.FactoryListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainAppFragmentController(fragment: Fragment, private val context: Context, view: View) {

    private val factories: FactoryListView = view.findViewById(R.id.factories_main)
    private val nested: NestedScrollView = view.findViewById(R.id.scroll_main)

    private val viewModel: MainAppViewModel = ViewModelProvider(fragment).get(MainAppViewModel::class.java)

    private var listFactory: List<Factory>? = viewModel.factories

    init {
        factories.emptyView.setOnActionListener {
            CoroutineScope(Dispatchers.IO).launch {
                load()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            if (listFactory == null) load()
            setList()
            withContext(Dispatchers.Main) {
                restoreState()
            }
        }
    }

    private suspend fun load() {
        factories.isLoad = true

        try {
            val factoryDAO = ServerController().factoryDAO
            val list = factoryDAO.getFactory()

            listFactory = list
        } catch (e: Exception) {
            factories.emptyInternetLost()
        }

    }

    private suspend fun setList() {
        withContext(Dispatchers.Main) {
            listFactory?.apply {
                viewModel.factories = this

                if (isNotEmpty()) {
                    factories.showList(reversed())
                } else {
                    factories.isEmpty = true
                }
            }
        }
    }

    private fun restoreState() {
        nested.scrollY = viewModel.state
        viewModel.state = 0
    }

    fun saveState() {
        viewModel.state = nested.scrollY
    }
}