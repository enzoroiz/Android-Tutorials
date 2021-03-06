package com.enzoroiz.tmdb.presentation.tvshow

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
import com.enzoroiz.tmdb.data.model.tvshow.TVShow
import com.enzoroiz.tmdb.databinding.ActivityTvShowsBinding
import com.enzoroiz.tmdb.presentation.di.core.Injector
import javax.inject.Inject

class TVShowsActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: TVShowViewModelFactory

    private lateinit var binding: ActivityTvShowsBinding

    private val adapter = TVShowAdapter()
    private val viewModel by viewModels<TVShowViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Injector).createTVShowSubComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_shows)
        binding.lstTVShows.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        getFromLiveData(viewModel.getTVShows())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.common, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_action_update -> {
                getFromLiveData(viewModel.updateTVShows())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getFromLiveData(liveData: LiveData<List<TVShow>>) {
        binding.progressBar.visibility = View.VISIBLE
        liveData.observe(this, Observer {
            adapter.updateItems(it)
            binding.progressBar.visibility = View.GONE
        })
    }
}