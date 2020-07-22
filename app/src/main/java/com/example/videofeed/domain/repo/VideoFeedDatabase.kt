package com.example.videofeed.domain.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.videofeed.domain.models.VideoFeedEntity

@Database(entities = [VideoFeedEntity::class], version = 1)
abstract class VideoFeedDatabase : RoomDatabase() {

    abstract fun videoFeedDao(): VideoFeedDao
}