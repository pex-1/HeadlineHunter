package com.example.headlinehunter.di

import com.example.headlinehunter.HeadlineHunterApp
import com.example.headlinehunter.MainViewModel
import com.example.headlinehunter.core.data.SyncFeedWorker
import com.example.headlinehunter.core.data.SyncFeedWorkerScheduler
import com.example.headlinehunter.core.domain.SyncFeedScheduler
import com.example.headlinehunter.ui.bookmarks.BookmarksViewModel
import com.example.headlinehunter.ui.details.DetailsViewModel
import com.example.headlinehunter.ui.home.HomeViewModel
import com.example.headlinehunter.ui.newsfeed.NewsFeedViewModel
import com.example.headlinehunter.ui.settings.SettingsViewModel
import com.example.headlinehunter.ui.unread.UnreadViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as HeadlineHunterApp).applicationScope
    }

    singleOf(::SyncFeedWorkerScheduler).bind<SyncFeedScheduler>()
    workerOf(::SyncFeedWorker)

    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::BookmarksViewModel)
    viewModelOf(::UnreadViewModel)
    viewModelOf(::NewsFeedViewModel)
    viewModelOf(::SettingsViewModel)
}