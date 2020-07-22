package com.example.videofeed.app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.videofeed.R

class VideoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, VideoPlayerFragment.newInstance())
                .commitNow()
        }
    }
}
