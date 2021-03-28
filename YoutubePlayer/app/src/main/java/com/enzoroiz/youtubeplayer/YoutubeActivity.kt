package com.enzoroiz.youtubeplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "HM2K_4lDpTw"
const val YOUTUBE_PLAYLIST_ID = "PLGYpOAL6OhdBnrVd26f4sNlK9HDmo88M7"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "YouTubeActivity"
    private val DIALOG_REQUEST_CODE = 1
    private val playerView by lazy { YouTubePlayerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Both approaches do the same thing
//        This first, setContentView inflates the xml resource and set the activity's view
//        to the one inflated from the xml resource
//        setContentView(R.layout.activity_youtube)
//        val layout: ConstraintLayout = findViewById(R.id.activity_youtube)

//        This second approach the view is inflated and stored as a variable
//        Then the we set the activity's view with the inflated resource xml
        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)

        playerView.initialize(getString(R.string.youtube_api_key), this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.d(TAG, "onInitializationSuccess")
        Toast.makeText(this, "Initialized successfully", Toast.LENGTH_LONG).show()

        player?.setPlaybackEventListener(playbackEventListener)
        player?.setPlayerStateChangeListener(playerStateChangeListener)
        if (!wasRestored) {
            player?.loadVideo(YOUTUBE_VIDEO_ID)
        } else {
            player?.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        initializationResult: YouTubeInitializationResult?
    ) {
        if (initializationResult?.isUserRecoverableError == true) { // Check if it's not null
            initializationResult.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
        } else {
            val errorMessage = "Error while initializing the YouTubePlayer ($initializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult called with requestCode $requestCode")

        if (requestCode == DIALOG_REQUEST_CODE) {
            Log.d(TAG, intent?.toString().toString())
            Log.d(TAG, intent?.extras?.toString().toString())
            playerView.initialize(getString(R.string.youtube_api_key), this)
        }
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
            // Do nothing
        }

        override fun onBuffering(p0: Boolean) {
            // Do nothing
        }

        override fun onStopped() {
            // Do nothing
        }

        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity, "Video is playing", Toast.LENGTH_LONG).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "Video has paused", Toast.LENGTH_LONG).show()
        }
    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onLoaded(p0: String?) {
            // Do nothing
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
            // Do nothing
        }

        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity, "Click the add!", Toast.LENGTH_LONG).show()
        }

        override fun onLoading() {
            Toast.makeText(this@YoutubeActivity, "Video is loading", Toast.LENGTH_LONG).show()
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity, "Video has started", Toast.LENGTH_LONG).show()
        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity, "Video has ended", Toast.LENGTH_LONG).show()
        }
    }
}
