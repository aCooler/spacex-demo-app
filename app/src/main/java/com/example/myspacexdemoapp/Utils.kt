package com.example.myspacexdemoapp

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toDateString(): String? {
    if (this.isEmpty()) {
        return this
    }
    val parser = SimpleDateFormat(BuildConfig.DATE_STRING_FROM, Locale.US)
    val formatter = SimpleDateFormat(BuildConfig.DATE_STRING_TO, Locale.US)
    return formatter.format(parser.parse(this) ?: "")
}
