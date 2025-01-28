package com.example.headlinehunter.ui.home

import com.example.headlinehunter.R
import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.core.domain.channel.Channel

data class HomeState(
    val isLoadingData: Boolean = true,
    val isRefreshing: Boolean = false,
    val channels: List<Channel> = emptyList(),
    val articles: List<Article> = emptyList(),
    val collapseFeedSelection: Boolean = false,
    val collapseMenuIcon: Int = R.drawable.collapse
)