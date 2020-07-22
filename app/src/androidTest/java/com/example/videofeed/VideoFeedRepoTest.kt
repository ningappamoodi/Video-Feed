package com.example.videofeed


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.videofeed.domain.repo.VideoFeedDao
import com.example.videofeed.domain.repo.VideoFeedDatabase
import com.example.videofeed.domain.repo.VideoFeedRepo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VideoFeedRepoTest {

    private lateinit var videoFeedDatabase: VideoFeedDatabase
    private lateinit var videoFeedDao: VideoFeedDao
    private lateinit var videoFeedRepo: VideoFeedRepo

    @Before
    fun before() {

        videoFeedDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            VideoFeedDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        videoFeedDao = videoFeedDatabase.videoFeedDao()
        videoFeedRepo = VideoFeedRepo(videoFeedDao)
    }
    @After
    fun after() {
        videoFeedDatabase.close()
    }

    @Test
    fun testGetVideoFeeds() {
        val videoFeeds = Utils.getVideoFeeds()
        runBlocking {
            videoFeedRepo.deleteAndCreateVideoFeed(videoFeeds)

            Assert.assertEquals(videoFeeds.size, videoFeedRepo.getVideoFeeds().size)
        }
    }

    @Test
    fun testNoVideoFeeds() {

        runBlocking {
            videoFeedDao.cleanVideoFeed()
            Assert.assertEquals(0, videoFeedRepo.getVideoFeeds().size)
        }
    }

    @Test
    fun isVideoFeedAvailableYes() {
        val videoFeeds = Utils.getVideoFeeds()
        runBlocking {
            videoFeedRepo.deleteAndCreateVideoFeed(videoFeeds)
            Assert.assertEquals(true, videoFeedRepo.isVideoFeedAvailable())
        }
    }

    @Test
    fun isVideoFeedAvailableNo() {
        runBlocking {
            videoFeedDao.cleanVideoFeed()
            Assert.assertEquals(false, videoFeedRepo.isVideoFeedAvailable())
        }
    }
}