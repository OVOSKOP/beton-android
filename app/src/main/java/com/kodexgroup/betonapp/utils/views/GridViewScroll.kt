package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.GridView


class GridViewScroll(context: Context, attrs: AttributeSet) : GridView(context, attrs) {

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        performClick()
        requestDisallowInterceptTouchEvent(true)
        return super.onTouchEvent(ev)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return false
    }

}