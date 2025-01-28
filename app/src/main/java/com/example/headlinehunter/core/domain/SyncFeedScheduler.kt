package com.example.headlinehunter.core.domain

interface SyncFeedScheduler {

    fun scheduleSyncFeedWorker(interval: Long)
    fun cancelSyncFeedWorker()

}