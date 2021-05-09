 package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.utils.spToPx

class RatingView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val star1: ImageView
    private val star2: ImageView
    private val star3: ImageView
    private val star4: ImageView
    private val star5: ImageView
    private val ratingCount: TextView

    var rating: Float = 0f
        set(value) {
            field = value

            if (rating >= 1) {
                setFull(star1)
            }
            if (rating >= 2) {
                setFull(star2)
            }
            if (rating >= 3) {
                setFull(star3)
            }
            if (rating >= 4) {
                setFull(star4)
            }
            if (rating == 5f) {
                setFull(star5)
            }

        }

    private var sizeStar: Int = 24
        set(value) {
            field = value


            setSize(star1, value)
            setSize(star2, value)
            setSize(star3, value)
            setSize(star4, value)
            setSize(star5, value)
        }

    var textSize: Float = spToPx(13f, context)
        set(value) {
            field = value

            ratingCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
        }

    var tintNull: Boolean = false
        set(value) {
            field = value

            if (value) {
                setTintNull(star1)
                setTintNull(star2)
                setTintNull(star3)
                setTintNull(star4)
                setTintNull(star5)
            }
        }

    var suffix: String = ""
        set(value) {
            field = value

            ratingCount.text = context.getString(R.string.rating, rating, value)
        }

    init {
        val root = inflate(context, R.layout.view_rating, this)

        star1 = root.findViewById(R.id.star_1)
        star2 = root.findViewById(R.id.star_2)
        star3 = root.findViewById(R.id.star_3)
        star4 = root.findViewById(R.id.star_4)
        star5 = root.findViewById(R.id.star_5)

        ratingCount = root.findViewById(R.id.rating_count)

        context.obtainStyledAttributes(attrs,
                R.styleable.RatingView, 0, 0)
                .apply {
                    try {
                        rating = getFloat(R.styleable.RatingView_rating, 0f)
                        textSize = getDimensionPixelSize(R.styleable.RatingView_textSize, 37).toFloat()
                        sizeStar = getDimensionPixelSize(R.styleable.RatingView_size, 24)
                        suffix = getString(R.styleable.RatingView_suffix) ?: ""
                        tintNull = getBoolean(R.styleable.RatingView_tintNull, false)
                        setTextColor(getColor(R.styleable.RatingView_textColor, ContextCompat.getColor(context, R.color.hint)))
                        ratingCount.visibility = if (getBoolean(R.styleable.RatingView_countRating, true)) VISIBLE else GONE
                    } finally { }
                }
                .recycle()
    }

    private fun setFull(image: ImageView) {
        image.setImageResource(R.drawable.ic_star_full)
        setTintNull(image)
    }

    private fun setTintNull(image: ImageView) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            image.imageTintList = null
        } else {
            // TODO("ADD VERSION)
        }
    }

    private fun setSize(image: ImageView, size: Int) {
        val params = image.layoutParams
        params.width = size
        params.height = size
        image.layoutParams = params
    }

    fun setTextColor(color: Int) {
        ratingCount.setTextColor(color)
    }

}