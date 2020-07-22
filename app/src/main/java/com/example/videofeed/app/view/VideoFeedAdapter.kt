package com.example.videofeed.app.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.videofeed.R
import com.example.videofeed.domain.models.VideoFeedEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_feed_item.view.*

class VideoFeedAdapter(private val context: Context?,  var storageUrl:String, private val videoFeed: MutableList<VideoFeedEntity>) :
    RecyclerView.Adapter<VideoFeedAdapter.FeedViewHolder>() {

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.video_feed_item, parent, false)

        return FeedViewHolder(view)
    }

    override fun getItemCount(): Int = videoFeed.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {

        videoFeed.let {

            val thumbImg =  storageUrl + it[position].thumb
            val url = storageUrl + it[position].source

            holder.itemView.title.text = it[position].title
            holder.itemView.subTitle.text = it[position].subtitle

            Picasso.get().load(thumbImg)
                .into(holder.itemView.thumbImage)

            holder.itemView.description.setText(it[position].description)

            holder.itemView.setOnClickListener {
                val intent = Intent(context, VideoPlayerActivity::class.java)
                intent.putExtra("thumbImage", thumbImg)
                intent.putExtra("url", url)
                context?.startActivity(intent)
            }
            playVideo(holder, url, position)
        }
    }

    private fun playVideo(v: FeedViewHolder, url: String, position: Int) {

        val video: Uri = Uri.parse(url)

        v.itemView.videoView.setVideoURI(video)
        v.itemView.videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
        }

        if (position == 0) {
            if(!v.itemView.videoView.isPlaying) {
                v.itemView.videoView.start()
                v.itemView.thumbImage.visibility = View.GONE
            }
        }
    }
}
