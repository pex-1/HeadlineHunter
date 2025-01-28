package com.example.headlinehunter.core.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.headlinehunter.core.domain.RssFeedRepository
import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.core.domain.util.Result.Error
import com.example.headlinehunter.core.domain.util.Result.Success
import kotlinx.coroutines.flow.first

class SyncFeedWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: RssFeedRepository
) : CoroutineWorker(context, params) {

    val notificationService = FeedNotificationService(context)

    override suspend fun doWork(): Result {
        val channels = repository.getSubscribedChannels()

        channels.forEach { channel ->
            val newArticles = channel.feedSync()
            if (newArticles > 0) {
                val lastArticle = repository.getArticles(listOf(channel.id)).first().take(1)
                notificationService.showNotification(channel, newArticles, lastArticle.first())
            }
        }

        return Result.success()
    }

    suspend fun Channel.feedSync(): Int {
        return when (val result = repository.fetchArticles(this.link, this.id)) {
            is Error -> 0

            is Success -> result.data

        }
    }
}
