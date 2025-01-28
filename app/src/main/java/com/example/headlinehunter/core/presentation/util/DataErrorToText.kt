package com.example.headlinehunter.core.presentation.util

import android.content.Context
import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.R

fun DataError.toText(context: Context): String {
    val resId = when(this) {
        DataError.Local.DISK_FULL -> R.string.error_disk_full
        DataError.Local.ALREADY_SUBSCRIBED -> R.string.already_subscribed
        DataError.Network.NO_INTERNET -> R.string.error_no_internet
        DataError.Network.RSS_PARSING_EXCEPTION -> R.string.rss_parsing_error
        else -> R.string.error_unknown
    }
    return context.getString(resId)
}