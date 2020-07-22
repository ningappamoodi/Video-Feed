package com.example.videofeed.domain.repo

import com.example.videofeed.domain.models.VideoFeedEntity

class VideoFeedRepo (private val videoFeedDao: VideoFeedDao) {

     suspend fun getVideoFeeds(): List<VideoFeedEntity> = videoFeedDao.getVideoFeeds()
     suspend fun deleteAndCreateVideoFeed(videoFeedList: List<VideoFeedEntity>) =
          videoFeedDao.deleteAndCreateVideoFeed(videoFeedList)

     suspend fun  isVideoFeedAvailable() =videoFeedDao.getVideoFeeds()?.isNotEmpty()
}