package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.utils.dpToPx

class SearchBlockView(context: Context, attributeSet: AttributeSet?) : LinearLayout(
    context,
    attributeSet
) {

    private val searchText: EditText
    private val hintText: TextView
    private val searchBtn: ImageButton
    private val searchBtnFocus: ImageButton
    private val root: View

    var hint: String = ""
        set(value) {
            field = value
            hintText.text = value
        }

    var isFocus: Boolean = false
        set(value) {
            field = value
            if (value) {
                searchText.setPadding(dpToPx(52f), dpToPx(8f), dpToPx(15f), dpToPx(8f))
                searchBtnFocus.visibility = View.VISIBLE

                searchText.setBackgroundResource(R.drawable.ic_edit_search_focus)

                hintText.setPadding(110, dpToPx(8f), dpToPx(15f), dpToPx(8f))

                searchBtn.visibility = View.INVISIBLE
                searchBtnFocus.visibility = View.VISIBLE
                searchText.requestFocus()
            } else {
                searchText.setPadding(dpToPx(15f), dpToPx(8f), dpToPx(15f), dpToPx(8f))
                searchBtnFocus.visibility = View.INVISIBLE

                searchText.setBackgroundResource(R.drawable.ic_edit_search)

                hintText.animation = null

                searchBtn.visibility = View.VISIBLE
                searchBtnFocus.visibility = View.INVISIBLE
                searchText.clearFocus()
            }
        }

    init {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        root = inflater.inflate(R.layout.view_search_block, this, true)

        searchText = root.findViewById(R.id.search_txt)
        hintText = root.findViewById(R.id.hint_search)
        searchBtn = root.findViewById(R.id.search_btn)
        searchBtnFocus = root.findViewById(R.id.search_btn_focus)

        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty() == true) {
                    hintText.visibility = INVISIBLE
                } else {
                    hintText.visibility = VISIBLE
                }
            }

        })

        searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                println("search")
                //TODO("ADD SEARCH")
                return@setOnEditorActionListener true
            }
            false
        }
    }

    fun lock(click: () -> Unit = {}) {
        searchText.isFocusable = false
        searchText.isFocusableInTouchMode = false
        searchText.inputType = EditorInfo.TYPE_NULL
        searchText.isLongClickable = false
        searchBtn.isEnabled = false

        searchText.isClickable = true

        searchText.setOnClickListener {
            click()
        }
    }

    fun setFocus(listener: () -> Unit = {}) {
        searchBtn.visibility = View.VISIBLE
        searchBtnFocus.visibility = View.VISIBLE

        searchText.isClickable = false

        searchText.setPadding(dpToPx(55f), dpToPx(8f), dpToPx(15f), dpToPx(8f))

        searchText.setBackgroundResource(R.drawable.ic_edit_search_focus)

        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_move_hint)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                searchText.requestFocus()
                listener()
            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })
        hintText.startAnimation(anim)

        val leftAnim = AnimationUtils.loadAnimation(context, R.anim.anim_move_btn)
        searchBtn.startAnimation(leftAnim)

        val leftAnimFocus = AnimationUtils.loadAnimation(context, R.anim.anim_move_btn_focus)
        searchBtnFocus.startAnimation(leftAnimFocus)
    }

    fun clearFocusForm(listener: () -> Unit = {}) {
        searchText.setPadding(dpToPx(15f), dpToPx(8f), dpToPx(15f), dpToPx(8f))
        searchBtn.visibility = View.VISIBLE
        searchBtnFocus.visibility = View.VISIBLE

        searchText.isClickable = true

        searchText.clearFocus()

        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_move_hint_reverse)
        hintText.startAnimation(anim)

        val leftAnim = AnimationUtils.loadAnimation(context, R.anim.anim_move_btn_reverse)
        leftAnim
            .setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    searchText.setBackgroundResource(R.drawable.ic_edit_search)
                }

                override fun onAnimationRepeat(animation: Animation?) {}

            })
        searchBtn.startAnimation(leftAnim)

        val leftAnimFocus = AnimationUtils.loadAnimation(
            context,
            R.anim.anim_move_btn_focus_reserve
        )
        leftAnimFocus.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                searchBtnFocus.visibility = View.INVISIBLE
                listener()
            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })
        searchBtnFocus.startAnimation(leftAnimFocus)
    }

}