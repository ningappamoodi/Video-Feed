package com.example.videofeed


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.videofeed.domain.repo.VideoFeedDao
import com.example.videofeed.domain.repo.VideoFeedDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VideoFeedDaoTest {

    private lateinit var videoFeedDatabase: VideoFeedDatabase
    private lateinit var videoFeedDao: VideoFeedDao

    @Before
    fun before() {

        videoFeedDatabase = Room.inMemoryDatabaseBuilder(
           InstrumentationRegistry.getInstrumentation().context,
           VideoFeedDatabase::class.java)
           .allowMainThreadQueries()
           .build()

        videoFeedDao = videoFeedDatabase.videoFeedDao()
    }
    @After
    fun after() {
        videoFeedDatabase.close()
    }

    @Test
    fun testGetVideoFeeds() {

        val videoFeeds = Utils.getVideoFeeds()

        runBlocking {
            videoFeedDao.deleteAndCreateVideoFeed(videoFeeds)
            Assert.assertEquals(videoFeeds.size, videoFeedDao.getVideoFeeds().size)
        }

    }

    @Test
    fun testNoVideoFeeds() {
        runBlocking {
            videoFeedDao.cleanVideoFeed()
            Assert.assertEquals(0, videoFeedDao.getVideoFeeds().size)
        }

    }
}