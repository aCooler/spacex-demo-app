package com.example.myspacexdemoapp

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import java.text.SimpleDateFormat
import java.util.*

fun String.toDateString(): String? {
    if (this.isEmpty()) {
        return this
    }
    val parser = SimpleDateFormat(BuildConfig.DATE_STRING_FROM, Locale.US)
    val formatter = SimpleDateFormat(BuildConfig.DATE_STRING_TO, Locale.US)
    return formatter.format(parser.parse(this) ?: "")
}

fun Drawable.setColor(colorInt: Int) {
    this.colorFilter =
        PorterDuffColorFilter(colorInt, PorterDuff.Mode.SRC_IN)
}
