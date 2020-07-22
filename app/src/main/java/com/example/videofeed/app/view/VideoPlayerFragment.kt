package com.example.videofeed.app.view


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.example.videofeed.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_player_fragment.*


class VideoPlayerFragment : Fragment() {

    companion object {
        fun newInstance() = VideoPlayerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.video_player_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val thumbImg = activity?.intent?.getStringExtra("thumbImage")
        val url = activity?.intent?.getStringExtra("url")

        closeBtn.setOnClickListener { activity?.finish() }

        thumbImage.visibility = View.VISIBLE

        Picasso.get().load(thumbImg).into(thumbImage)
        playVideo(url)
    }

    private fun playVideo(url: String?) {

        val video: Uri = Uri.parse(url)

        val mc = MediaController(context)
        mc.setAnchorView(videoView)
        mc.setMediaPlayer(videoView)

        videoView.setVideoURI(video)
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            videoView.start()
            videoView.visibility = View.VISIBLE
            thumbImage.visibility = View.GONE
        }

        videoView.setOnClickListener {
            if(mc.isShowing) mc.hide() else mc.show()
        }
    }
}
