package com.enzoroiz.youtubeplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_choice.*
import java.lang.IllegalArgumentException

class ChoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        btnPlayVideo.setOnClickListener(this::onClick)
        btnPlayPlaylist.setOnClickListener(this::onClick)
    }

    private fun onClick(view: View) {
        val youtubeApiKey = getString(R.string.youtube_api_key)
        val intent = when (view.id) {
            R.id.btnPlayVideo -> YouTubeStandalonePlayer.createVideoIntent(this, youtubeApiKey, YOUTUBE_VIDEO_ID, 0, true, false)
            R.id.btnPlayPlaylist -> YouTubeStandalonePlayer.createPlaylistIntent(this, youtubeApiKey, YOUTUBE_PLAYLIST_ID, 0, 0, true, true)
            else -> throw IllegalArgumentException("Undefined button clicked")
        }

        startActivity(intent)
    }
}