package com.example.headlinehunter.di

import com.example.headlinehunter.core.data.networking.RssRemoteDataSource
import com.example.headlinehunter.core.domain.RemoteDataSource
import com.prof18.rssparser.RssParserBuilder
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single {
        RssParserBuilder().build()
    }

    singleOf(::RssRemoteDataSource).bind<RemoteDataSource>()
}