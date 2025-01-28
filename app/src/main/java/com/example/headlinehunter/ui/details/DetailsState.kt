package com.example.headlinehunter.ui.details

import com.example.headlinehunter.core.domain.article.Article

data class DetailsState(
    val article: Article = Article()
)