package com.example.headlinehunter.ui.unread

import com.example.headlinehunter.core.domain.article.Article

sealed interface UnreadAction {
    data object OnBackClick : UnreadAction
    data object OnAddRssFeedClick : UnreadAction
    data class OnArticleClick(val articleId: Int) : UnreadAction
    data class OnFavoritesClick(val article: Article) : UnreadAction
}