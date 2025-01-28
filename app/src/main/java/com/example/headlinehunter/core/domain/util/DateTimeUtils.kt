package com.example.headlinehunter.core.domain.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun String?.getTimeInMillis(): Long {

    if (this.isNullOrEmpty()) return Calendar.getInstance().timeInMillis

    var timeFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.getDefault())
    val time = timeFormat.parse(this)
    return time.time
}

fun Long.getDateFromMills(): String {
    val formatter = SimpleDateFormat("EEE dd, HH:mm", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}