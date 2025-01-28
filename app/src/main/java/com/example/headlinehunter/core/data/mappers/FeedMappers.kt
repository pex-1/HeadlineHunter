package com.example.headlinehunter.core.data.mappers

import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.core.domain.util.getTimeInMillis
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem

fun RssChannel.toChannel(rssLink: String): Channel {
    return Channel(
        title = title ?: "",
        description = description ?: "",
        link = rssLink,
        image = image?.url ?: "",
        lastUpdateDate = lastBuildDate ?: ""
    )
}

fun RssItem.toArticle(): Article {
    return Article(
        title = title ?: "",
        author = author ?: "",
        link = link ?: "",
        pubDate = pubDate.getTimeInMillis(),
        image = image ?: "",
        description = description ?: "",
        isFavorite = false
    )
}