package com.pixiedia.pixicommerce.utils.ktx

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.vvalidator.util.onTextChanged
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ViewPager2.makeItASlider() {
    clipChildren = false
    clipToPadding = false
    offscreenPageLimit = 3
    val rv = getChildAt(0) as RecyclerView
    rv.clipToPadding = false
    rv.setPadding(85, 0, 10, 0)
}

fun MaterialCardView.setCorners(
    topLeft: Float = 0f,
    bottomLeft: Float = 0f,
    bottomRight: Float = 0f,
    topRight: Float = 0f,
    all: Float = 0f
) {
    shapeAppearanceModel = if (all.toInt() == 0)
        shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, bottomLeft)
            .setBottomRightCorner(CornerFamily.ROUNDED, bottomRight)
            .setTopLeftCorner(CornerFamily.ROUNDED, topLeft)
            .setTopRightCorner(CornerFamily.ROUNDED, topRight)
            .build()
    else
        shapeAppearanceModel
            .toBuilder()
            .setAllCornerSizes(all)
            .build()
}

fun MaterialCardView.setBackgroundColorEX(colorId: Int) =
    setCardBackgroundColor(ContextCompat.getColorStateList(this.context, colorId))

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun combineClickListeners(vararg views: View?, function: () -> Unit) {
    views.forEach {
        it?.setOnClickListener {
            function.invoke()
        }
    }
}

fun combineLongClickListeners(vararg views: View?, passing: Boolean = false, function: () -> Unit) {
    views.forEach {
        it?.setOnLongClickListener {
            function.invoke()
            return@setOnLongClickListener passing
        }
    }
}

/**
 * shows this @receiver View and perform this
 * function inside the click listener
 */
fun View.setOnClickListenerWithVisibility(function: () -> Unit) = with(this) {
    show()
    setOnClickListener { function.invoke() }
}

fun isNotEmptyOrNullEditTextsObserver(vararg views: EditText, function: (Boolean) -> Unit) {

    // store valid or not boolean for every view in the views
    val validList = mutableListOf<Boolean>().apply {
        repeat(views.count()) { this.add(false) }
    }

    views.forEachIndexed { index, editText ->
        editText.onTextChanged(debounce = 300) {
            // set True if text is not empty or null
            validList[index] = (!it.isNullOrBlank())

            // if all == True trigger the function
            function(!validList.contains(false))
        }
    }
}
