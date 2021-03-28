package com.enzoroiz.flickrbrowser

import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.content_photo_details.*

class PhotoDetailsActivity : BaseActivity() {
    private val TAG = "PhotoDetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_photo_details)
        activateToolbar(true)

        val photo = intent.getParcelableExtra(PHOTO_TRANSFER) as Photo
        txt_photo_title.text = resources.getString(R.string.photo_title_text, photo.title)
        txt_photo_tags.text = resources.getString(R.string.photo_tags_text, photo.tags)
        txt_photo_author.text = photo.author

        Picasso.get()
            .load(photo.link)
            .error(R.drawable.ic_placeholder)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_photo)
    }

}
