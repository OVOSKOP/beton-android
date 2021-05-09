package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.kodexgroup.betonapp.R

class EmptyView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val titleText: TextView
    private val subTextTxt: TextView
    private val actionTxt: TextView

    var title: String = ""
        set(value) {
            field = value

            titleText.text = value
        }

    var subText: String = ""
        set(value) {
            field = value

            subTextTxt.text = value
        }

    var actionTitle: String = ""
        set(value) {
            field = value

            actionTxt.text = value
        }

    init {
        val root = inflate(context, R.layout.view_empty_lists, this)

        titleText = root.findViewById(R.id.title_empty)
        subTextTxt = root.findViewById(R.id.sub_text_empty)
        actionTxt = root.findViewById(R.id.action_empty)

        context.obtainStyledAttributes(attrs,
                R.styleable.EmptyView, 0, 0)
                .apply {
                    try {
                        title = getString(R.styleable.EmptyView_headText).toString()
                        subText = getString(R.styleable.EmptyView_subText).toString()
                        actionTitle = getString(R.styleable.EmptyView_actionLabel).toString()
                    } finally { }
                }
                .recycle()
    }

    fun setOnActionListener(listener: () -> Unit) {
        actionTxt.setOnClickListener {
            listener()
        }
    }

}