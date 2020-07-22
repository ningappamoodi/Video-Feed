package com.example.videofeed.di

import com.example.videofeed.data.apiservices.VideoFeedService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


 private val BASEURL = "https://video-sample-android.free.beeceptor.com/"
 //   private const val BASEURL = "https://run.mocky.io/v3/dfe94561-e26a-405b-9813-516b20d86fe1/"

val networkModule = module {
    single { provideRetrofit() }
    factory { provideVideoFeedService(get()) }
    }

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(BASEURL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build() }

    fun  provideVideoFeedService(retrofit: Retrofit) : VideoFeedService {
        return retrofit.create(VideoFeedService::class.java)
    }