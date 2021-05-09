package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.screens.productlist.ProductListFragment
import com.kodexgroup.betonapp.utils.adapters.FactoryListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class FactoryListView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val listFactory: RecyclerView
    private val loaded: ProgressBar
    val emptyView: EmptyView

    private val adapter =  FactoryListAdapter(context)

    var isEmpty: Boolean = false
        set(value) {
            field = value

            if (value) {
                loaded.visibility = GONE
                listFactory.visibility = INVISIBLE
                emptyView.visibility = VISIBLE
            } else {
                loaded.visibility = GONE
                listFactory.visibility = VISIBLE
                emptyView.visibility = GONE
            }
        }

    var isLoad: Boolean = true
        set(value) {
            field = value

            if (value) {
                emptyView.visibility = GONE
                loaded.visibility = VISIBLE
                listFactory.visibility = INVISIBLE
            } else {
                loaded.visibility = GONE
                listFactory.visibility = VISIBLE
                emptyView.visibility = GONE
            }
        }

    init {
        val root = inflate(context, R.layout.view_factory_list, this)

        listFactory = root.findViewById(R.id.factory_list)
        loaded = root.findViewById(R.id.loaded_factory_list)
        emptyView = root.findViewById(R.id.empty_factory_list)

        listFactory.layoutManager = LinearLayoutManager(context)

        listFactory.adapter = adapter
    }

    fun emptyInternetLost() {
        emptyView.title = "Отсутствует подключение к интернету."
        emptyView.subText = "Проверьте ваше подключение или обновите список."

        isEmpty = true

    }

    fun userNone() {
        emptyView.title = "Отсутствует аккаунт."
        emptyView.subText = "Перейдите на вкладку Профиль и войдите в аккаунт."
        emptyView.actionTitle = "Профиль"

        isEmpty = true
    }

    fun showList(list: List<Factory>) {
        adapter.reload(list)

        isLoad = false

    }
}