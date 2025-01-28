package com.example.headlinehunter.ui.home

import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.core.domain.channel.Channel

sealed interface HomeAction {
    data object OnBackClick : HomeAction
    data class OnArticleClick(val articleId: Int) : HomeAction
    data class OnAddToFavoritesClick(val article: Article) : HomeAction
    data class OnChannelClick(val channel: Channel) : HomeAction
    data object OnPullToRefresh : HomeAction
    data object OnAddRssFeedClick : HomeAction
    data class OnCollapseChannelsClick(val collapse: Boolean, val icon: Int) : HomeAction
}