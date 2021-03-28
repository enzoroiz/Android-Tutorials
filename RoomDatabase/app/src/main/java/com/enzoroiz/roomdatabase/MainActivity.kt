package com.enzoroiz.roomdatabase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.enzoroiz.roomdatabase.database.AppDatabase
import com.enzoroiz.roomdatabase.database.SubscriberRepository
import com.enzoroiz.roomdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SubscriberRecyclerViewAdapter
    private val viewModel by viewModels<MainActivityViewModel> {
        MainActivityViewModelFactory(
            application,
            SubscriberRepository(AppDatabase.getInstance(applicationContext).subscriberDAO)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = SubscriberRecyclerViewAdapter { viewModel.setSelectedSubscriber(it) }
        binding.lstSubscribers.layoutManager = LinearLayoutManager(this)
        binding.lstSubscribers.adapter = adapter

        updateSubscribersList()

        viewModel.eventLiveData.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateSubscribersList() {
        viewModel.subscribers.observe(this, Observer { subscribers ->
            adapter.updateSubscribers(subscribers)
        })
    }
}