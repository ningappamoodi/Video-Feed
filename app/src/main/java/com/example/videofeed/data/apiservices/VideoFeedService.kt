package com.example.videofeed.data.apiservices

import com.example.videofeed.data.model.VideoFeed
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface VideoFeedService {

    @GET(".")
   suspend  fun getVideoFeedAsync() : VideoFeed

}
