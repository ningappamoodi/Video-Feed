package com.example.videofeed

import com.example.videofeed.app.viewmodel.VideoFeedViewModel
import com.example.videofeed.data.model.VideoFeed
import com.example.videofeed.domain.models.VideoFeedEntity
import com.example.videofeed.domain.usecases.VideoFeedUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class VideoFeedViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()
    @MockK
    lateinit var videoFeedUseCase: VideoFeedUseCase
    lateinit var videoFeedViewModel: VideoFeedViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        videoFeedViewModel = VideoFeedViewModel(videoFeedUseCase)
        Dispatchers.setMain(testDispatcher)

    }

   @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun fetchVideoFeeds() {

        val videoFeed: VideoFeed =  mockk()
        val videos: List<VideoFeedEntity> = mockk()

        coEvery { videoFeedUseCase.getVideoFeeds() } returns videos
        coEvery { videoFeedUseCase.fetchVideoFeedAsync() } returns videoFeed
        coEvery { videoFeedUseCase.insertVideoFeed(videos) } returns Unit

        coEvery { videoFeed.storageUrl } returns "http://"
        coEvery { videoFeed.videos } returns videos
        coEvery { videos.isEmpty() } returns false


        runBlocking {
            videoFeedViewModel.fetchVideoFeeds() }

        coVerify { videoFeedUseCase.fetchVideoFeedAsync() }
    }
}