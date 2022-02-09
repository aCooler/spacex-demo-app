package com.example.myspacexdemoapp

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

fun Drawable.setColor(colorInt: Int) {
    this.colorFilter =
        PorterDuffColorFilter(colorInt, PorterDuff.Mode.SRC_IN)
}

var TextView.textValue: String
    get() {
        return text.toString()
    }
    set(value) {
        if (value.isEmpty()) {
            visibility = View.GONE
        } else {
            text = value
        }
    }


fun getTopHeight(card : ConstraintLayout): Int {
    var result = 0
    val resourceId: Int =
        card.context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = card.context.resources.getDimensionPixelSize(resourceId)
    }

    return result
}