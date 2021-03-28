package com.enzoroiz.top10downloader

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "FeedViewModel"
private const val INVALIDATED_FEED = "INVALIDATED"
val EMPTY_FEED_LIST = listOf<FeedEntry>()

class FeedViewModel : ViewModel() {
    private val feedLiveData = MutableLiveData<List<FeedEntry>>()
    val feedEntries: LiveData<List<FeedEntry>>
        get() = feedLiveData

    private var downloadData: DownloadData? = null
    private var feedCachedURL = INVALIDATED_FEED

    init {
        feedLiveData.postValue(EMPTY_FEED_LIST)
    }

    fun downloadFeed(feedUrl: String) {
        Log.d(TAG, "downloadFeed() called with $feedUrl")
        if (feedUrl != feedCachedURL) {
            downloadData = DownloadData { feedLiveData.value = it }
            downloadData?.execute(feedUrl)
            feedCachedURL = feedUrl
        }
    }

    fun invalidateFeed() {
        feedCachedURL = INVALIDATED_FEED
    }

    override fun onCleared() {
        Log.d(TAG, "Canceling pending downloads")
        downloadData?.cancel(true)
    }
}