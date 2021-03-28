package com.enzoroiz.basicdatabinding

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.enzoroiz.basicdatabinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.button.setOnClickListener {
            toggleProgressBar()
        }
    }

    private fun toggleProgressBar() {
        with(binding) {
            if (progressBar.visibility == View.VISIBLE) {
                progressBar.visibility = View.INVISIBLE
                button.text = getString(R.string.btn_label_show_progress_bar)
            } else {
                progressBar.visibility = View.VISIBLE
                button.text = getString(R.string.btn_label_hide_progress_bar)
            }
        }
    }
}