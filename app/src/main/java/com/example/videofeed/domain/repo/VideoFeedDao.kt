package com.example.videofeed.domain.repo

import androidx.room.*
import com.example.videofeed.domain.models.VideoFeedEntity

@Dao
interface VideoFeedDao {

    @Query("SELECT * FROM VideoFeedEntity")
  suspend  fun getVideoFeeds(): List<VideoFeedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoFeeds(articles: List<VideoFeedEntity>)

    @Query("DELETE FROM VideoFeedEntity")
    suspend  fun cleanVideoFeed()

    @Transaction
    suspend fun deleteAndCreateVideoFeed(videoFeedList: List<VideoFeedEntity>) {
        cleanVideoFeed()
        insertVideoFeeds(videoFeedList)
    }
}
