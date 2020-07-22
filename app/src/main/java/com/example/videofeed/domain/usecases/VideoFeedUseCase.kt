package com.example.videofeed.domain.usecases

import com.example.videofeed.data.apiservices.VideoFeedService
import com.example.videofeed.domain.models.VideoFeedEntity
import com.example.videofeed.domain.repo.VideoFeedRepo

class VideoFeedUseCase(private val videoFeedService: VideoFeedService, private val videoFeedRepo: VideoFeedRepo) {

    suspend fun fetchVideoFeedAsync() = videoFeedService.getVideoFeedAsync()

   suspend fun insertVideoFeed(videoFeedList: List<VideoFeedEntity>) = videoFeedRepo.deleteAndCreateVideoFeed(videoFeedList)

   suspend fun  isVideoFeedAvailable() = videoFeedRepo.isVideoFeedAvailable()

    suspend fun getVideoFeeds() =  videoFeedRepo.getVideoFeeds()
}