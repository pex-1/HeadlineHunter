package com.example.headlinehunter.database.di

import androidx.room.Room
import com.example.headlinehunter.core.data.RssFeedRepositoryImpl
import com.example.headlinehunter.core.domain.DataStorePreferences
import com.example.headlinehunter.core.domain.RssFeedRepository
import com.example.headlinehunter.core.domain.article.LocalArticleDataSource
import com.example.headlinehunter.core.domain.channel.LocalChannelDataSource
import com.example.headlinehunter.database.DataStorePreferencesImpl
import com.example.headlinehunter.database.RoomLocalArticleDataSource
import com.example.headlinehunter.database.RoomLocalChannelDataSource
import com.example.headlinehunter.database.RssDatabase
import com.example.headlinehunter.database.util.DatabaseConstants
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RssDatabase::class.java,
            DatabaseConstants.DATABASE
        ).build()
    }

    single { get<RssDatabase>().channelDao }
    single { get<RssDatabase>().articleDao }

    singleOf(::DataStorePreferencesImpl).bind<DataStorePreferences>()

    singleOf(::RssFeedRepositoryImpl).bind<RssFeedRepository>()
    singleOf(::RoomLocalChannelDataSource).bind<LocalChannelDataSource>()
    singleOf(::RoomLocalArticleDataSource).bind<LocalArticleDataSource>()
}