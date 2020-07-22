package com.example.videofeed.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videofeed.domain.models.VideoFeedEntity
import com.example.videofeed.domain.usecases.VideoFeedUseCase
import kotlinx.coroutines.launch


class VideoFeedViewModel(private val videoFeedUseCase: VideoFeedUseCase): ViewModel() {

    private val _videoFeedLiveData: MutableLiveData<List<VideoFeedEntity>> = MutableLiveData()
    private val _emptyLiveData: MutableLiveData<Any> = MutableLiveData()
    private val _storageUrlLiveData: MutableLiveData<String> = MutableLiveData()

    val videoFeedLiveData: LiveData<List<VideoFeedEntity>> = _videoFeedLiveData
    val emptyLiveData: LiveData<Any> =_emptyLiveData
    val storageUrlLiveData: LiveData<String> =_storageUrlLiveData

    fun fetchVideoFeeds() {

        loadVideoFeeds()
        viewModelScope.launch {
            try {
                val videoFeed = videoFeedUseCase.fetchVideoFeedAsync()
                _storageUrlLiveData.postValue(videoFeed.storageUrl)

                videoFeed.videos?.let {
                   videoFeedUseCase.insertVideoFeed(it)
                    loadVideoFeeds()
                }
                if(videoFeed.videos.isNullOrEmpty()) _emptyLiveData.postValue(0)

            }catch (e:Exception) {
                e.printStackTrace()
                isVideoFeedAvailable()
            }
        }

    }

    private fun isVideoFeedAvailable() = viewModelScope.launch { if(!videoFeedUseCase.isVideoFeedAvailable())
        _emptyLiveData.postValue(0) }

    private fun loadVideoFeeds() {
        viewModelScope.launch {

            val videoFeeds = videoFeedUseCase.getVideoFeeds()

            if(videoFeeds.isNotEmpty()) _videoFeedLiveData.postValue(videoFeeds)

        }
    }



}
