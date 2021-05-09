package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.utils.dialogs.SearchFormDialog
import com.kodexgroup.betonapp.utils.findParentNavController
import com.kodexgroup.betonapp.utils.getFragmentManager


class SearchFormView(context: Context, attributeSet: AttributeSet) : LinearLayout(
        context,
        attributeSet
) {

    private val searchLock: SearchBlockView

    private var fm: FragmentManager? = null

    init {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_search_form, this, true)

        searchLock = root.findViewById(R.id.search_lock)

        if (!isInEditMode) {
            fm = getFragmentManager(context)
        }

        searchLock.lock {

            searchLock.setFocus {

                findParentNavController().navigate(R.id.to_dialog)
                findParentNavController().addOnDestinationChangedListener { _, destination, _ ->
                    if (destination.id == R.id.homeFragment) {
                        val animFadeIn = AnimationUtils.loadAnimation(context, R.anim.anim_fade_in)
                        searchLock.startAnimation(animFadeIn)

                        searchLock.clearFocusForm()
                    }
                }
            }

        }

    }

}