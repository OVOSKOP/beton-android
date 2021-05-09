package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.utils.findParent

class ButtonFilterView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val button: TextView
    private val activeView: View

    private val ratingBtn: LinearLayout
    private val ratingView: RatingView

    var isInFilterView = false

    private var listener: (() -> Unit)? = null

    var isActive: Boolean = false
        set(value) {
            field = value

            if (value) {
                button.setBackgroundResource(R.drawable.ic_filter_border_active)
                button.setTextColor(ContextCompat.getColor(context, R.color.yellow_700))
                activeView.visibility = View.VISIBLE

                ratingBtn.setBackgroundResource(R.drawable.ic_filter_border_active)
                ratingView.setTextColor(ContextCompat.getColor(context, R.color.yellow_700))
            } else {
                button.setBackgroundResource(R.drawable.ic_filter_border_normal)
                button.setTextColor(ContextCompat.getColor(context, R.color.hint))
                activeView.visibility = View.INVISIBLE

                ratingBtn.setBackgroundResource(R.drawable.ic_filter_border_normal)
                ratingView.setTextColor(ContextCompat.getColor(context, R.color.hint))
            }
        }

    var factory: String? = null
        set(value) {
            field = value

            isActive = false
            button.setBackgroundResource(R.drawable.ic_filter_border_factory)
            button.setTextColor(ContextCompat.getColor(context, R.color.blue_500))

            text = value ?: "Button"
        }

    var rating: Float = 0f
        set(value) {
            field = value

            if (value != -1f) {
                button.visibility = GONE

                ratingBtn.visibility = VISIBLE
                ratingView.rating = value
            }
        }

    var suffix: String = ""
        set(value) {
            field = value

            ratingView.suffix = value
        }

    var text: String = ""
        set(value) {
            field = value

            button.text = text
        }

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_filter_button, this, true)

        button = root.findViewById(R.id.btn_filter)
        activeView = root.findViewById(R.id.active_btn)

        ratingBtn = root.findViewById(R.id.rating_btn)
        ratingView = root.findViewById(R.id.rating_view)

        context.obtainStyledAttributes(attrs,
                R.styleable.ButtonFilterView, 0, 0)
                .apply {
                    try {
                        isActive = getBoolean(R.styleable.ButtonFilterView_active, false)
                        text = getString(R.styleable.ButtonFilterView_text) ?: "Button"
                        rating = getFloat(R.styleable.ButtonFilterView_ratingCount, -1f)
                        suffix = getString(R.styleable.ButtonFilterView_ratingSuffix) ?: ""
                    } finally { }
                }
                .recycle()

        onClick()
    }

    fun setOnClickBtnListener(listener: () -> Unit) {
        this.listener = listener
        onClick()
    }

    private fun onClick() {
        button.setOnClickListener {
            if (factory == null) {
                isActive = !isActive

                listener?.invoke()

                if (isInFilterView) {
                    findParent<FiltersView>(parent)?.deleteBtn(this)
                }
            }
        }

        ratingBtn.setOnClickListener {
            isActive = !isActive

            listener?.invoke()

            if (isInFilterView) {
                findParent<FiltersView>(parent)?.deleteBtn(this)
            }
        }
    }
}