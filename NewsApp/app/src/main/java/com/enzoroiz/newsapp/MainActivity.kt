package com.enzoroiz.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.enzoroiz.newsapp.databinding.ActivityMainBinding
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModel
import com.enzoroiz.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<NewsViewModel>{ viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnvMain.setupWithNavController(
            fragmentNavigationHost.findNavController()
        )
    }
}