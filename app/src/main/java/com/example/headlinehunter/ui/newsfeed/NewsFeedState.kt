package com.example.headlinehunter.ui.newsfeed

import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.core.domain.util.DataError

data class NewsFeedState(
    val feedLink: String = "",
    val linkError: DataError? = null,
    val showNewFeedDialog: Boolean = false,
    val channel: Channel? = null,
    val channels: List<Channel> = emptyList(),
    val showConfirmDeleteDialog: Boolean = false,
    val showNotificationSettingsDialog: Boolean = false
)