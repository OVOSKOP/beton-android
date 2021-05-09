package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.kodexgroup.betonapp.R

class ReviewBlockView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val countTxt: TextView
    private val rating: RatingView
    private val card: CardView
    private val listView: LinearLayout
    private val open: TextView
    private val empty: TextView

    init {
        val root = inflate(context, R.layout.view_review_block, this)

        countTxt = root.findViewById(R.id.count_title_review)
        rating = root.findViewById(R.id.total_rating)
        card = root.findViewById(R.id.card_reviews)
        listView = root.findViewById(R.id.list_preview_review)
        open = root.findViewById(R.id.open_all_review)
        empty = root.findViewById(R.id.empty_review)
    }

    fun addReviews(list: List<Int>, ratingCount: Float) {
        if (list.isNotEmpty()) {
            countTxt.text = list.size.toString()
            empty.visibility = GONE

            if (list.size > 2) {
                open.visibility = VISIBLE
            }
        } else {
            card.visibility = GONE
            countTxt.text = "0"
        }

        card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))

        countTxt.background = null
        val param = countTxt.layoutParams
        param.width = ViewGroup.LayoutParams.WRAP_CONTENT

        countTxt.layoutParams = param
        rating.rating = ratingCount
    }

}