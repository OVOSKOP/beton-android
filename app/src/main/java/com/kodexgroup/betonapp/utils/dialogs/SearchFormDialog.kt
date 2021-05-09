package com.kodexgroup.betonapp.utils.dialogs

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.screens.home.HomeFragment
import com.kodexgroup.betonapp.utils.views.DialogContentView
import com.kodexgroup.betonapp.utils.views.MiniCardProductView
import com.kodexgroup.betonapp.utils.views.SearchBlockView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFormDialog : DialogFragment() {

    private lateinit var searchLock: SearchBlockView
    private lateinit var history: DialogContentView
    private lateinit var specialForYou: LinearLayout
    private lateinit var popular: DialogContentView

    private var products: List<Product?>? = null

    private var listener: (() -> Unit)? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.dialog_search, container, false)



        searchLock = root.findViewById(R.id.dialog_search_form)
        history = root.findViewById(R.id.history_search)
        popular = root.findViewById(R.id.add_search)
        specialForYou = root.findViewById(R.id.special_product)

        history.addMiniButtons(listOf("", "", ""))
        popular.addMiniButtons(listOf("", "", ""))

        history.setOnBtnClickListener {
            // TODO("DELETE HISTORY")
            println("Delete")
        }

        popular.setOnBtnClickListener {
            // TODO("RELOAD POPULAR")
            println("Reload")
        }

        CoroutineScope(Dispatchers.IO).launch {
            getProducts()
            setProd()
        }

        return root
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setGravity(Gravity.TOP or Gravity.START)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        setFocus()
    }

    fun setOnDismissListener(listener: () -> Unit) {
        this.listener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        searchLock.clearFocusForm()


        findNavController().popBackStack()
        listener?.let { it() }
    }

    private fun setFocus() {
        searchLock.isFocus = true
    }

    private suspend fun getProducts() {
        val dao = ServerController().productDAO

        val productsArr = dao.getProducts()
        if (productsArr.isNotEmpty()) {

            products = productsArr
        }
    }

    private suspend fun setProd() {
        withContext(Dispatchers.Main) {
            val param = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1.0f
            )

            val prod = mutableListOf<Product?>()

            if (products == null) {

                specialForYou.visibility = View.GONE
            } else {
                specialForYou.visibility = View.VISIBLE

                prod.add(products!!.last())
                prod.add(products!!.elementAtOrNull(products!!.lastIndex - 1))
            }

            for (p in prod) {
                val card = MiniCardProductView(requireContext(), null)

                card.setOnClickCardListener {
                    findNavController().popBackStack()
                    val home = parentFragment?.childFragmentManager?.fragments?.get(0) as HomeFragment

                    home.toProduct(it)

                }
                card.addProduct(p)

                card.layoutParams = param
                specialForYou.addView(card)
            }
        }
    }
}