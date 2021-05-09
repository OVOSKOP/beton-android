package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.kodexgroup.betonapp.R

class ButtonGridView(context: Context, attr: AttributeSet?) : LinearLayout(context, attr) {

    private val btn: Button

    var title: String = ""
        set(value) {
            field = value

            btn.text = value
        }

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_search_item_grid_item, this, true)

        btn = root.findViewById(R.id.button_grid_item)

        context.obtainStyledAttributes(attr,
                R.styleable.ButtonGridView, 0, 0)
                .apply {
                    try {
                        title = getString(R.styleable.ButtonGridView_titleBtn) ?: "Button"
                    } finally { }
                }
                .recycle()

        btn.setOnClickListener {
            println("search")
            // TODO("ADD SEARCH")
        }
    }

}