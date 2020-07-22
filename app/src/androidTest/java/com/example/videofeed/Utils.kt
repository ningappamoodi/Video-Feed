package com.example.videofeed

import com.example.videofeed.data.model.VideoFeed
import com.example.videofeed.domain.models.VideoFeedEntity
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

object Utils {

    fun getVideoFeeds(): List<VideoFeedEntity> =  getVideoFeeds(readJsonFromResource("res/raw/video_feed.json"))

     private fun getVideoFeeds(videoFeedJsonObj: JsonObject): List<VideoFeedEntity> {

        val jsonArray = videoFeedJsonObj.getAsJsonArray("videos")

        val videoFeedList = mutableListOf<VideoFeedEntity>()
        for (jsonElement: JsonElement in jsonArray) {
            val videoFeedEntity =
                GsonBuilder().create().fromJson(jsonElement, VideoFeedEntity::class.java)
            videoFeedList.add(videoFeedEntity)
        }
        return videoFeedList
    }

    fun getStorageUrl() =  getStorageUrl(readJsonFromResource("res/raw/video_feed.json"))

     private fun getStorageUrl(videoFeedJsonObj: JsonObject): String = GsonBuilder().create()
         .fromJson(videoFeedJsonObj.get("storageUrl"), String::class.java)



    fun getVideoFeed(videoFeedJsonObj: JsonObject): VideoFeed {
        val videoFeed:VideoFeed = VideoFeed()
        videoFeed.storageUrl =
            getStorageUrl(videoFeedJsonObj)
        videoFeed.videos =
            getVideoFeeds(videoFeedJsonObj)
        return videoFeed
    }

    private fun readJsonFromResource(resourcePath: String): JsonObject {
        val text = javaClass.classLoader?.getResourceAsStream(resourcePath)
            ?.bufferedReader()?.use { it.readText() }

        return JsonParser().parse(text).asJsonObject
    }
}