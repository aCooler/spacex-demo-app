package com.example.myspacexdemoapp

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable

fun Drawable.setColor(colorInt: Int) {
    this.colorFilter =
        PorterDuffColorFilter(colorInt, PorterDuff.Mode.SRC_IN)
}
