package com.example.videofeed

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(VideoFeedUseCaseTest::class, VideoFeedViewModelTest::class)
class MainTestSuite