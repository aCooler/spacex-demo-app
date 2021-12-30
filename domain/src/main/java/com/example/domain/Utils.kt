package com.example.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toDateString(): String {
    return SimpleDateFormat("MMM dd , yyyy HH:mm a", Locale.US)
        .format(this)
}
