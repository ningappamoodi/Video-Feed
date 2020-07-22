package com.example.videofeed.di

import com.example.videofeed.app.viewmodel.VideoFeedViewModel
import org.koin.dsl.module

val viewModule = module {
    factory { VideoFeedViewModel(get()) }
}