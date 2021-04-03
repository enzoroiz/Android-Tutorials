package com.enzoroiz.tmdb.presentation.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.enzoroiz.tmdb.R
import com.enzoroiz.tmdb.data.model.movie.Movie
import com.enzoroiz.tmdb.databinding.ActivityMoviesBinding
import com.enzoroiz.tmdb.presentation.di.core.Injector
import javax.inject.Inject

class MoviesActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: MovieViewModelFactory

    private lateinit var binding: ActivityMoviesBinding

    private val adapter = MovieAdapter()
    private val viewModel by viewModels<MovieViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Injector).createMovieSubComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies)
        binding.lstMovies.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        getFromLiveData(viewModel.getMovies())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.common, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_action_update -> {
                getFromLiveData(viewModel.updateMovies())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getFromLiveData(liveData: LiveData<List<Movie>>) {
        binding.progressBar.visibility = View.VISIBLE
        liveData.observe(this, Observer {
            adapter.updateItems(it)
            binding.progressBar.visibility = View.GONE
        })
    }
}