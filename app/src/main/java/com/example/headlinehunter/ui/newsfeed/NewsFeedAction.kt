package com.example.headlinehunter.ui.newsfeed

import com.example.headlinehunter.core.domain.channel.Channel

sealed interface NewsFeedAction {
    data object OnBackClick : NewsFeedAction

    data class OnValueChanged(val feedLink: String) : NewsFeedAction
    data object OnNewFeedDialogSearch : NewsFeedAction
    data class OnShowNewFeedDialog(val showNewFeedDialog: Boolean) : NewsFeedAction
    data object OnNewFeedDialogSubscribe : NewsFeedAction
    data class OnShowConfirmDeleteDialog(val channel: Channel?) : NewsFeedAction
    data object OnDeleteChannel : NewsFeedAction
    data object OnShowGoToSettingsDialog : NewsFeedAction
    data class OnSubscribeClick(val channel: Channel) : NewsFeedAction
}