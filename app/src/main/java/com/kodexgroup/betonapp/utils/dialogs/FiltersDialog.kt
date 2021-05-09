package com.kodexgroup.betonapp.utils.dialogs

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.utils.FilterCode
import com.kodexgroup.betonapp.utils.dpToPx
import com.kodexgroup.betonapp.utils.views.ButtonFilterView
import java.util.ArrayList

class FiltersDialog : DialogFragment() {

    private var listener: ((ArrayList<Int>) -> Unit)? = null

    private lateinit var saleBtn: ButtonFilterView
    private lateinit var nearBtn: ButtonFilterView
    private lateinit var newestBtn: ButtonFilterView

    private lateinit var rating2: ButtonFilterView
    private lateinit var rating3: ButtonFilterView
    private lateinit var rating4: ButtonFilterView
    private lateinit var rating5: ButtonFilterView

    private lateinit var ready: Button
    private lateinit var miss: Button

    private var filters: ArrayList<Int> = arrayListOf()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.dialog_filters, container, false)

        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation

        saleBtn = root.findViewById(R.id.dialog_sale_btn)
        nearBtn = root.findViewById(R.id.dialog_near_btn)
        newestBtn = root.findViewById(R.id.dialog_newest_btn)
        miss = root.findViewById(R.id.miss_btn)

        rating2 = root.findViewById(R.id.rating_2)
        rating3 = root.findViewById(R.id.rating_3)
        rating4 = root.findViewById(R.id.rating_4)
        rating5 = root.findViewById(R.id.rating_5)

        ready = root.findViewById(R.id.all_ready)

        filters = arguments?.getIntegerArrayList("filters") ?: arrayListOf()
        for (code in filters) {
            when (code) {
                FilterCode.NEAR -> nearBtn.isActive = true
                FilterCode.SALE -> saleBtn.isActive = true
                FilterCode.NEW -> newestBtn.isActive = true
            }
        }

        saleBtn.setOnClickBtnListener {
            if (saleBtn.isActive)
                filters.add(FilterCode.SALE)
            else
                filters.remove(FilterCode.SALE)
        }

        nearBtn.setOnClickBtnListener {
            if (nearBtn.isActive)
                filters.add(FilterCode.NEAR)
            else
                filters.remove(FilterCode.NEAR)
        }

        newestBtn.setOnClickBtnListener {
            if (newestBtn.isActive)
                filters.add(FilterCode.NEW)
            else
                filters.remove(FilterCode.NEW)
        }

        rating2.setOnClickBtnListener {
            if (rating2.isActive)
                filters.add(FilterCode.RATING_2)
            else
                filters.remove(FilterCode.RATING_2)
        }

        rating3.setOnClickBtnListener {
            if (rating3.isActive)
                filters.add(FilterCode.RATING_3)
            else
                filters.remove(FilterCode.RATING_3)
        }

        rating4.setOnClickBtnListener {
            if (rating4.isActive)
                filters.add(FilterCode.RATING_4)
            else
                filters.remove(FilterCode.RATING_4)
        }

        rating5.setOnClickBtnListener {
            if (rating5.isActive)
                filters.add(FilterCode.RATING_5)
            else
                filters.remove(FilterCode.RATING_5)
        }

        ready.setOnClickListener {
            dismiss()
        }

        miss.setOnClickListener {
            filters = arrayListOf()
            dismiss()
        }

        return root
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setGravity(Gravity.TOP or Gravity.END)
        dialog?.window?.setLayout(dpToPx(320f), ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

    fun setOnDismissListener(listener: (ArrayList<Int>) -> Unit) {
        this.listener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        listener?.invoke(filters)
    }

}