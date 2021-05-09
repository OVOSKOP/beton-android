package com.kodexgroup.betonapp.utils.views

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.screens.productlist.ProductListFragment
import com.kodexgroup.betonapp.utils.ProductType
import com.kodexgroup.betonapp.utils.findParentNavController
import com.kodexgroup.betonapp.utils.getImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ButtonTypeView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private val btn: CardView
    private val previewType: ImageView
    private val titleTypeTxt: TextView

    private var type = 0

    private var navController: NavController? = null
    private var fragment: ProductListFragment? = null

    var title: String = ""
        set(value) {
            field = value

            titleTypeTxt.text = value
        }

    private var preview: Int = R.color.hint
        set(value) {
            field = value

            CoroutineScope(Dispatchers.Main).launch {
                getImage(value, previewType)
            }
        }

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_menu_types, this, true)

        btn = root.findViewById(R.id.type_menu_btn)
        previewType = root.findViewById(R.id.preview_type_card)
        titleTypeTxt = root.findViewById(R.id.title_type_card)

        context.obtainStyledAttributes(attributeSet,
                R.styleable.ButtonTypeView, 0, 0)
                .apply {
                    try {
                        title = getString(R.styleable.ButtonTypeView_typeName) ?: "Бетоны"
                        preview = getResourceId(R.styleable.ButtonTypeView_typePreview, R.color.hint)
                        type = getInt(R.styleable.ButtonTypeView_typeId, 0)
                    } finally { }
                }
                .recycle()

        btn.setOnClickListener {
            val args = fragment?.arguments ?: Bundle()
            val types = args.getIntegerArrayList("type") ?: arrayListOf()

            if (type in types) {
                types.remove(type)
            } else {
                types.add(type)
            }

            args.putIntegerArrayList("type", types)
            println(args)

            if (fragment == null) {
                navController?.navigate(R.id.to_product_list, args)
            } else {
                if (fragment?.arguments != null) {
                    fragment?.arguments?.putIntegerArrayList("type", types)
                } else {
                    fragment?.arguments = args
                }
                fragment?.onReloadedState()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (!isInEditMode) {
            navController = findNavController()

            try {
                fragment = findFragment()
            } catch (e: ClassCastException) { }

            reloadAlpha()

        }
    }

    fun reloadAlpha() {
        val types = fragment?.arguments?.getIntegerArrayList("type") ?: arrayListOf()
        alpha = if (types.isNotEmpty() && type !in types) {
            0.5F
        } else {
            1f
        }
    }
}