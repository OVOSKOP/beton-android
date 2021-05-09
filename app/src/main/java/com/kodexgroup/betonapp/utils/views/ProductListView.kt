package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.screens.productlist.ProductListFragment
import com.kodexgroup.betonapp.utils.FilterCode
import com.kodexgroup.betonapp.utils.adapters.ProductListAdapter
import com.kodexgroup.betonapp.utils.findParentNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*


class ProductListView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    val listProduct: RecyclerView
    private val loaded: ProgressBar
    val emptyView: EmptyView

    var isEmpty: Boolean = false
        set(value) {
            field = value

            if (value) {
                loaded.visibility = GONE
                listProduct.visibility = GONE
                emptyView.visibility = VISIBLE
            } else {
                loaded.visibility = GONE
                listProduct.visibility = VISIBLE
                emptyView.visibility = GONE
            }
        }

    var isLoad: Boolean = false
        set(value) {
            field = value

            if (value) {
                emptyView.visibility = GONE
                loaded.visibility = VISIBLE
                listProduct.visibility = GONE
            } else {
                loaded.visibility = GONE
                listProduct.visibility = VISIBLE
                emptyView.visibility = GONE
            }
        }

    init {
        val root = inflate(context, R.layout.view_product_list, this)

        listProduct = root.findViewById(R.id.list_product)
        loaded = root.findViewById(R.id.loaded_product_list)
        emptyView = root.findViewById(R.id.empty_product_list)


        println("Create")

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
}