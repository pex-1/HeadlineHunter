package com.example.headlinehunter.core.data

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.headlinehunter.core.domain.SyncFeedScheduler
import java.util.concurrent.TimeUnit

class SyncFeedWorkerScheduler(
    private val context: Context
) : SyncFeedScheduler {

    val workManager = WorkManager.getInstance(context)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    override fun scheduleSyncFeedWorker(interval: Long) {

        var syncFeedWorkRequest = PeriodicWorkRequestBuilder<SyncFeedWorker>(
            repeatInterval = interval,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .build()

        workManager
            .enqueueUniquePeriodicWork(
                PERIODIC_WORK,
                ExistingPeriodicWorkPolicy.KEEP,
                syncFeedWorkRequest
            )
    }

    override fun cancelSyncFeedWorker() {
        workManager.cancelAllWork()
    }

    companion object {
        const val PERIODIC_WORK = "periodic_work"
    }
}