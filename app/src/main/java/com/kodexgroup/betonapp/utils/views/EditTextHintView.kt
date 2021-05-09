package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.kodexgroup.betonapp.R


class EditTextHintView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private var editText: EditText
    private var hintText: TextView
    private var errorText: TextView
    private var isFull = false

    private var hint: String = ""
        set(value) {
            field = value
            hintText.text = hint
        }
    private var isPassword: Boolean = false
        set(value) {
            field = value
            if (value) {
                editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

    var isError: Boolean = false
        set(value) {
            field = value
            if (value) {
                editText.background = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_edit_text_error, null)
            } else {
                editText.background = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_edit_text, null)
                errorText.text = ""
            }
        }

    val text: String
        get() = editText.text.toString()

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_edit_text, this, true)

        editText = root.findViewById(R.id.input_txt)
        hintText = root.findViewById(R.id.input_txt_hint)
        errorText = root.findViewById(R.id.error_txt)

        context.obtainStyledAttributes(attributeSet,
            R.styleable.EditTextHintView, 0, 0)
            .apply {
                try {
                    hint = getString(R.styleable.EditTextHintView_hint).toString()
                    isPassword = getBoolean(R.styleable.EditTextHintView_password, false)
                } finally { }
            }
            .recycle()

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isFull = true
                if (editText.text.isEmpty()) {
                    val hintAnim = AnimationUtils.loadAnimation(context, R.anim.anim_hint_focus)
                    hintText.startAnimation(hintAnim)
                }
            } else {
                isFull = false
                if (editText.text.isEmpty()) {
                    val hintAnim = AnimationUtils.loadAnimation(context, R.anim.anim_hint_unfocus)
                    hintText.startAnimation(hintAnim)
                }
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty() == true && !isFull) {
                    val hintAnim = AnimationUtils.loadAnimation(context, R.anim.anim_hint_focus)
                    hintText.startAnimation(hintAnim)
                }
            }

        })
    }

    fun isError(error: String) {
        isError = true
        errorText.text = error
    }

    override fun clearFocus() {
        editText.clearFocus()
    }

    fun clear() {
        editText.setText("")
    }

}