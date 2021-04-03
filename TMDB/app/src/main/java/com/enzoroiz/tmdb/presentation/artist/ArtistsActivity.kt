package com.enzoroiz.tmdb.presentation.artist

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
import com.enzoroiz.tmdb.data.model.artist.Artist
import com.enzoroiz.tmdb.databinding.ActivityArtistsBinding
import com.enzoroiz.tmdb.presentation.di.core.Injector
import javax.inject.Inject

class ArtistsActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ArtistViewModelFactory

    private lateinit var binding: ActivityArtistsBinding

    private val adapter = ArtistAdapter()
    private val viewModel by viewModels<ArtistViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Injector).createArtistSubComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_artists)
        binding.lstArtists.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        getFromLiveData(viewModel.getArtists())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.common, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.mnu_action_update -> {
                getFromLiveData(viewModel.updateArtists())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getFromLiveData(livedata: LiveData<List<Artist>>) {
        binding.progressBar.visibility = View.VISIBLE
        livedata.observe(this, Observer {
            adapter.updateItems(it)
            binding.progressBar.visibility = View.GONE
        })
    }
}