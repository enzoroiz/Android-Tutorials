package com.enzoroiz.tmdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.enzoroiz.tmdb.databinding.ActivityMainBinding
import com.enzoroiz.tmdb.presentation.artist.ArtistsActivity
import com.enzoroiz.tmdb.presentation.movie.MoviesActivity
import com.enzoroiz.tmdb.presentation.tvshow.TVShowsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(binding) {
            btnOpenMoviesActivity.setOnClickListener { openActivity(MoviesActivity::class.java) }
            btnOpenTVShowsActivity.setOnClickListener { openActivity(TVShowsActivity::class.java) }
            btnOpenArtistsActivity.setOnClickListener { openActivity(ArtistsActivity::class.java) }
        }
    }

    private fun <T> openActivity(activity: Class<T>) {
        startActivity(Intent(this, activity))
    }
}