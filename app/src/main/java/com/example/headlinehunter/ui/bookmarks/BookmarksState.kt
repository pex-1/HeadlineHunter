package com.example.headlinehunter.ui.bookmarks

import com.example.headlinehunter.core.domain.article.Article

data class BookmarksState(
    val isLoading: Boolean = false,
    val favoriteArticles: List<Article> = emptyList()
)