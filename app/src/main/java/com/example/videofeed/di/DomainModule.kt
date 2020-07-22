package com.example.videofeed.di

import androidx.room.Room
import com.example.videofeed.domain.repo.VideoFeedRepo
import com.example.videofeed.domain.repo.VideoFeedDatabase
import com.example.videofeed.domain.usecases.VideoFeedUseCase
import org.koin.core.scope.Scope
import org.koin.dsl.module

val dbModule = module {
    single { provideVideoFeedDatabase() }
    single { videoFeedDao() }
    single { VideoFeedRepo(get()) }
}

private fun Scope.videoFeedDao() = get<VideoFeedDatabase>().videoFeedDao()

val useCaseModule = module {
    factory { VideoFeedUseCase(get(), get()) }
}

private fun Scope.provideVideoFeedDatabase() =
    Room.databaseBuilder(get(), VideoFeedDatabase::class.java, "db-feed")
        .fallbackToDestructiveMigration().build()
