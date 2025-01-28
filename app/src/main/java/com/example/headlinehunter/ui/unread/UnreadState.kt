package com.example.headlinehunter.ui.unread

import com.example.headlinehunter.core.domain.article.Article

data class UnreadState(
    val articles: List<Article> = emptyList()
)