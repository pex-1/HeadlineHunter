package com.example.headlinehunter.ui.bookmarks

import com.example.headlinehunter.core.domain.article.Article

sealed interface BookmarksAction {
    data object OnBackClick : BookmarksAction
    data class OnArticleClick(val articleId: Int) : BookmarksAction
    data class OnRemoveFromFavoritesClick(val article: Article) : BookmarksAction
}