package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.kodexgroup.betonapp.R

class DialogContentView(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {

    private val inflater: LayoutInflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val root: View = inflater.inflate(R.layout.view_search_dialog_item, this, true)

    var titleText: String = ""
        set(value) {
            field = value

            title.text = value
        }

    var iconBtn: Int = 0
        set(value) {
            field = value

            btn.setImageResource(iconBtn)
        }

    private var grid: FlexboxLayout
    private var title: TextView
    private var btn: ImageButton

    init {
        grid = root.findViewById(R.id.grid_flex)
        title = root.findViewById(R.id.title_dialog_item)
        btn = root.findViewById(R.id.dialog_item_btn)

        context.obtainStyledAttributes(attr,
            R.styleable.DialogContentView, 0, 0)
            .apply {
                try {
                    titleText = getString(R.styleable.DialogContentView_title) ?: "Title"
                    iconBtn = getResourceId(R.styleable.DialogContentView_iconBtn, R.drawable.ic_delete)
                } finally { }
            }
            .recycle()
    }

    fun addMiniButtons(list: List<String>) {
        for (item in list) {
            grid.addView(ButtonGridView(context, null))
        }
    }

    fun setOnBtnClickListener(listener: () -> Unit) {
        btn.setOnClickListener{
            listener()
        }
    }

}