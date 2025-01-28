package com.example.headlinehunter.core.domain

import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.Result

interface RemoteDataSource {
    suspend fun fetchRssChannel(channelLink: String): Result<Channel, DataError.Network>
    suspend fun fetchNewsFeed(channelLink: String): Result<List<Article>, DataError.Network>
}