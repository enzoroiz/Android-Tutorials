package com.enzoroiz.youtubeplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlaySingle.setOnClickListener(this::onClick)
        btnChoice.setOnClickListener(this::onClick)
    }

    private fun onClick(view: View) {
        val intent = when (view.id) {
            R.id.btnPlaySingle -> Intent(this, YoutubeActivity::class.java)
            R.id.btnChoice -> Intent(this, ChoiceActivity::class.java)
            else -> throw IllegalArgumentException("Undefined button clicked")
        }

        startActivity(intent)
    }
}
