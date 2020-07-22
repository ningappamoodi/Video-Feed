package com.example.videofeed.data.model

import androidx.room.Embedded
import com.example.videofeed.domain.models.VideoFeedEntity
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Deferred

class VideoFeed  {

    @SerializedName("storageUrl")
    var storageUrl: String? = null

    @Embedded
    @SerializedName("videos")
    var videos: List<VideoFeedEntity>? = null
}
