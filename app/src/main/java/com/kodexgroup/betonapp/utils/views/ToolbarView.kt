package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.utils.dialogs.SearchFormDialog
import com.kodexgroup.betonapp.utils.getFragmentManager

class ToolbarView(context: Context, attributeSet: AttributeSet) : Toolbar(context, attributeSet) {

    private val searchBtn: ImageButton

    var search: Boolean = false
        set(value) {
            field = value

            if (value) {
                searchBtn.visibility = View.VISIBLE
            } else {
                searchBtn.visibility = View.INVISIBLE
            }
        }

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.toolbar_app_default, this, true)

        searchBtn = root.findViewById(R.id.search_toolbar)
        searchBtn.setOnClickListener {
            findNavController().navigate(R.id.to_dialog)
        }

//        setNavigationOnClickListener {
//            val navController = findNavController()
//            navController.popBackStack()
//        }

        context.obtainStyledAttributes(attributeSet,
                R.styleable.ToolbarView, 0, 0)
                .apply {
                    try {
                        search = getBoolean(R.styleable.ToolbarView_search, false)
                    } finally { }
                }
                .recycle()
    }

}