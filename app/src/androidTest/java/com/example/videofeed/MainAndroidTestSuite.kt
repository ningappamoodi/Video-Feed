package com.example.videofeed

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(VideoFeedDaoTest::class, VideoFeedRepoTest::class)
class MainAndroidTestSuite