package com.example.videofeed.app.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videofeed.R
import com.example.videofeed.app.viewmodel.VideoFeedViewModel
import com.example.videofeed.domain.models.VideoFeedEntity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_feed_item.view.*
import kotlinx.android.synthetic.main.video_feed_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class VideoFeedFragment: Fragment() {

    companion object {
        fun newInstance() = VideoFeedFragment()
    }

    private val viewModel: VideoFeedViewModel by viewModel()
    private  lateinit var videoFeed:MutableList<VideoFeedEntity>
    private lateinit var adapter: VideoFeedAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.video_feed_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Picasso.get().isLoggingEnabled = true

        videoFeed = mutableListOf()
        adapter = VideoFeedAdapter(context,  getStorageUrl(),  videoFeed)
        layoutManager = LinearLayoutManager(context)

        video_feed_list.layoutManager = layoutManager
        video_feed_list.adapter = adapter

        viewModel.storageUrlLiveData.observe(viewLifecycleOwner, Observer { saveStorageUrl(it) })

        viewModel.videoFeedLiveData.observe(viewLifecycleOwner, Observer { setAdapterData(it) })

        viewModel.emptyLiveData.observe(viewLifecycleOwner, Observer { retryLoadData() })

        video_feed_list.addOnScrollListener(onScrollListener())

        fetchVideoFeeds()
    }

    private fun onScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            var currentPosition: Int = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    val oldPos = currentPosition
                    currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()

                    setPlay(oldPos, currentPosition)
                }
            }

            fun setPlay(oldPos: Int, currentPosition: Int) {

                val oldView = video_feed_list.findViewHolderForAdapterPosition(oldPos)
                oldView?.itemView?.thumbImage?.visibility = View.VISIBLE
                oldView?.itemView?.videoView?.pause()

                val newView =
                    video_feed_list.findViewHolderForAdapterPosition(currentPosition)
                newView?.itemView?.thumbImage?.visibility = View.GONE
                newView?.itemView?.videoView?.start()
            }
        }
    }

    private fun setAdapterData(it: List<VideoFeedEntity>) {
        progressBar.visibility = View.GONE
        videoFeed.clear()
        videoFeed.addAll(it.asIterable())
        adapter.storageUrl = getStorageUrl()
        adapter.notifyDataSetChanged()
    }

    private fun saveStorageUrl(it: String?) {
        val sharedPreferences = context?.getSharedPreferences(
            "APP_DATA",
            Context.MODE_PRIVATE
        )
        sharedPreferences?.edit()?.putString("storageUrl", it)?.commit()
    }

    private fun getStorageUrl() =
        activity?.getSharedPreferences("APP_DATA", Context.MODE_PRIVATE)
            ?.getString("storageUrl", "") ?: ""

    private fun retryLoadData() {
        progressBar.visibility = View.GONE
        Snackbar.make(video_feed_list, getString(R.string.no_data), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) {
                fetchVideoFeeds()
            }
            .show()
    }

    private fun fetchVideoFeeds() {
        progressBar.visibility = View.VISIBLE
        Handler().postDelayed({
            viewModel.fetchVideoFeeds()
        }, 1000)
    }

    override fun onResume() {
        super.onResume()

        startVideo()
    }

    override fun onPause() {
        super.onPause()

        stopVideo()
    }

    private fun startVideo() {
        val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        val newView =
            video_feed_list.findViewHolderForAdapterPosition(currentPosition)
        newView?.itemView?.thumbImage?.visibility = View.GONE
        newView?.itemView?.videoView?.start()
    }

    private fun stopVideo() {
        val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        val newView =
            video_feed_list.findViewHolderForAdapterPosition(currentPosition)
        newView?.itemView?.videoView?.stopPlayback()
    }
}