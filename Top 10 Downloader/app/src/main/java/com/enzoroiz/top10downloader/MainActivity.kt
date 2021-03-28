package com.enzoroiz.top10downloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class FeedEntry {
    var name = ""
    var artist = ""
    var releaseDate = ""
    var summary = ""
    var imageURL = ""

    override fun toString(): String {
        return """App: $name
        |From: $artist
        |Released on: $releaseDate""".trimMargin("|")
    }
}

private const val FEED_URL = "FEED_URL"
private const val FEED_LIMIT = "FEED_LIMIT"

class MainActivity : AppCompatActivity() {

    private var feedLimit = 10
    private var feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
    private val viewModel: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val feedAdapter = FeedAdapter(this, R.layout.list_entry, EMPTY_FEED_LIST)
        listView.adapter = feedAdapter

        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString(FEED_URL, feedUrl)
            feedLimit = savedInstanceState.getInt(FEED_LIMIT, feedLimit)
        }

        viewModel.feedEntries.observe(this, Observer { feedAdapter.setFeedList(it) })
        viewModel.downloadFeed(feedUrl.format(feedLimit))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FEED_URL, feedUrl)
        outState.putInt(FEED_LIMIT, feedLimit)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)
        if (feedLimit == 10) menu?.findItem(R.id.mnuTop10)?.isChecked = true
        if (feedLimit == 25) menu?.findItem(R.id.mnuTop25)?.isChecked = true

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuRefresh -> viewModel.invalidateFeed()
            R.id.mnuFree -> feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
            R.id.mnuPaid -> feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"
            R.id.mnuSongs -> feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
            R.id.mnuTop10 -> {
                feedLimit = 10
                item.isChecked = true
            }
            R.id.mnuTop25 -> {
                feedLimit = 25
                item.isChecked = true
            }
        }

        viewModel.downloadFeed(feedUrl.format(feedLimit))
        return true
    }
}
