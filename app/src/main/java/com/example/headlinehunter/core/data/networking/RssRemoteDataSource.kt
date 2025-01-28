package com.example.headlinehunter.core.data.networking

import com.example.headlinehunter.core.data.mappers.toArticle
import com.example.headlinehunter.core.data.mappers.toChannel
import com.example.headlinehunter.core.domain.RemoteDataSource
import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.Result
import com.example.headlinehunter.core.domain.util.map
import com.prof18.rssparser.RssParser

class RssRemoteDataSource(
    private val client: RssParser
) : RemoteDataSource {
    override suspend fun fetchRssChannel(channelLink: String): Result<Channel, DataError.Network> {
        return client.get(
            link = channelLink
        ).map { response ->
            response.toChannel(channelLink)
        }
    }

    override suspend fun fetchNewsFeed(channelLink: String): Result<List<Article>, DataError.Network> {
        return client.get(
            link = channelLink
        ).map { response ->
            response.items.map { it.toArticle() }
        }
    }

}