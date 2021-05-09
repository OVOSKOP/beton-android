package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.kodexgroup.betonapp.R

class ReviewView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_review, this)
    }

}