package com.example.videofeed.app

import android.app.Application
import com.example.videofeed.di.dbModule
import com.example.videofeed.di.viewModule
import com.example.videofeed.di.networkModule
import com.example.videofeed.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VideoFeedApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin { androidContext(this@VideoFeedApplication)
        modules(networkModule, dbModule, useCaseModule, viewModule) }
    }
}