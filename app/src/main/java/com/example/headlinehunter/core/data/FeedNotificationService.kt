package com.example.headlinehunter.core.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.headlinehunter.MainActivity
import com.example.headlinehunter.R
import com.example.headlinehunter.core.domain.article.Article
import com.example.headlinehunter.core.domain.channel.Channel

class FeedNotificationService(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val baseNotification by lazy {
        NotificationCompat.Builder(context, FEED_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.magnifying_glass_icon)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            FEED_NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.notification_feed),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Feed notifications channel"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(channel: Channel, numberOfArticles: Int, article: Article) {
        createNotificationChannel()

        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_MUTABLE
        )

        val notification = baseNotification
            .setContentTitle("${channel.title} - $numberOfArticles new")
            .setContentText(article.title)
            .setContentIntent(activityPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val FEED_NOTIFICATION_CHANNEL_ID = "feed_channel"
    }
}