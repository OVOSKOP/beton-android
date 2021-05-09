package com.kodexgroup.betonapp.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewParent
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.kodexgroup.betonapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine

fun Activity.hideKeyword() {
    val view = currentFocus ?: View(this)

    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.hideKeyword() {
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.hideKeyword() {
    val view = view ?: View(context)

    val imm: InputMethodManager =
        requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.dpToPx(dp: Float) : Int {
    val scale: Float = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun Fragment.dpToPx(dp: Float) : Int {
    val scale: Float = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun spToPx(sp: Float, context: Context) : Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
}

fun View.getFragmentManager(context: Context?) : FragmentManager? {
    return when (context) {
        is AppCompatActivity -> context.supportFragmentManager
        is ContextThemeWrapper -> getFragmentManager(context.baseContext)
        else -> null
    }
}

fun View.getImage(resId: Int, imageView: ImageView) {
    Glide
        .with(this@getImage)
        .load(resId)
        .placeholder(R.drawable.ic_placeholder_image)
        .into(imageView)
}

fun View.findParentNavController() : NavController {
    return Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
}

inline fun <reified T : View> findParent(parent: ViewParent) : T? {
    var isParent = parent
    while (isParent !is T) {
        if (isParent.parent == null) {
            return null
        }
        isParent = isParent.parent
    }
    return isParent
}

inline fun <reified T : Fragment> findFragment(parent: Fragment) : T? {
    var isParent = parent
    while (isParent !is T) {
        if (isParent.parentFragment == null) {
            return null
        }
        isParent = isParent.requireParentFragment()
    }
    return isParent
}

fun NavController.isEmpty() : Boolean {
    return previousBackStackEntry == null
}

val Fragment.app: App
    get() {
        return (requireContext().applicationContext as App)
    }

val View.app: App
    get() {
        return context.applicationContext as App
    }