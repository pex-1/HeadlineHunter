package com.example.headlinehunter.database.mappers

import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.database.entity.ArticleEntity

fun ArticleEntity.toArticle(): Article {
    return Article(
        id = id,
        title = title,
        description = description,
        author = author,
        link = link,
        image = image,
        pubDate = pubDate,
        isFavorite = isFavorite,
        channelId = channelId,
        isArticleRead = isArticleRead
    )
}

fun Article.toArticleEntity(channelId: Int? = null): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        description = description,
        author = author,
        link = link,
        image = image,
        pubDate = pubDate,
        isFavorite = isFavorite,
        channelId = channelId ?: this.channelId,
        isArticleRead = isArticleRead
    )
}