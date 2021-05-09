package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import com.kodexgroup.betonapp.R

class NotificationBtnView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private var noteBtn: ImageButton
    private var note: View

    var isNote = false
        set(value) {
            field = value

            if (value) {
                note.visibility = View.VISIBLE
            } else {
                note.visibility = View.INVISIBLE
            }
        }

    init {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_note_btn, this, true)

        noteBtn = root.findViewById(R.id.note_btn)
        note = root.findViewById(R.id.note_view)
    }

}