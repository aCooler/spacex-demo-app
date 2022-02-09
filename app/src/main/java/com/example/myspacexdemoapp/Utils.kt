package com.example.myspacexdemoapp

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView

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
