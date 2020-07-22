package com.example.videofeed

import com.example.videofeed.data.apiservices.VideoFeedService
import com.example.videofeed.data.model.VideoFeed
import com.example.videofeed.domain.models.VideoFeedEntity
import com.example.videofeed.domain.repo.VideoFeedDao
import com.example.videofeed.domain.repo.VideoFeedRepo
import com.example.videofeed.domain.usecases.VideoFeedUseCase
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class VideoFeedUseCaseTest {
    private lateinit var videoFeedUseCase: VideoFeedUseCase

    private val videoFeedService: VideoFeedService = mockk()
    private val videoFeedDao: VideoFeedDao = mockk()
    private val videoFeedRepo: VideoFeedRepo = spyk(VideoFeedRepo(videoFeedDao))
    @Before
    fun before() {
        videoFeedUseCase = VideoFeedUseCase(videoFeedService, videoFeedRepo)
    }
    @Test
    fun testFetchVideoFeedAsync() {

        val videoFeed:VideoFeed = mockk()
        val storageUrl:String = "http://"

        every { videoFeed.videos } returns mockk()
        every { videoFeed.videos?.size } returns 5
        every { videoFeed.storageUrl } returns storageUrl

        coEvery {videoFeedService.getVideoFeedAsync()} returns videoFeed

        runBlocking { val videoFeedAsynch = videoFeedUseCase.fetchVideoFeedAsync()
            assertEquals(5, videoFeedAsynch.videos?.size)
            assertEquals(storageUrl, videoFeedAsynch.storageUrl)
        }
    }

    @Test
     fun insertVideoFeed() {

         val videoFeedList:List<VideoFeedEntity> = mockk()

        coEvery { videoFeedDao.deleteAndCreateVideoFeed(videoFeedList) } returns Unit

         runBlocking { videoFeedUseCase.insertVideoFeed(videoFeedList) }

         coVerify { videoFeedRepo.deleteAndCreateVideoFeed(videoFeedList) }
     }

    @Test
     fun  isVideoFeedAvailableYes() {
         coEvery { videoFeedRepo.isVideoFeedAvailable() } returns true
         runBlocking { val isFeedAvailable = videoFeedUseCase.isVideoFeedAvailable()
             assertEquals(true, isFeedAvailable)}

     }
    @Test
    fun  isVideoFeedAvailableNo() {
        coEvery { videoFeedRepo.isVideoFeedAvailable() } returns false
        runBlocking { val isFeedAvailable = videoFeedUseCase.isVideoFeedAvailable()
            assertEquals(false, isFeedAvailable)}

    }

    @Test
     fun getVideoFeeds() {
        val videoFeedsList:List<VideoFeedEntity> = mockk()
        coEvery { videoFeedRepo.getVideoFeeds() } returns videoFeedsList
        runBlocking { val videoFeeds = videoFeedUseCase.getVideoFeeds()
            assertEquals(videoFeedsList, videoFeeds)
        }
     }

}