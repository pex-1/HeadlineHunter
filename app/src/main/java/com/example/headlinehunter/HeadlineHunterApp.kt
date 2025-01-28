package com.example.headlinehunter

import android.app.Application
import com.example.headlinehunter.database.di.databaseModule
import com.example.headlinehunter.di.appModule
import com.example.headlinehunter.di.networkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class HeadlineHunterApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@HeadlineHunterApp)
            workManagerFactory()
            modules(
                appModule,
                networkModule,
                databaseModule
            )
        }
    }
}