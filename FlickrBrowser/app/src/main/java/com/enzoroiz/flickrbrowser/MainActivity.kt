package com.enzoroiz.flickrbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*
import java.lang.Exception

private const val TAG = "MainActivity"

class MainActivity : BaseActivity(),
    RawDataProvider.OnDownloadCompleted,
    FlickrDataParser.OnDataAvailable,
    RecyclerItemClickListener.OnRecyclerClickListener {

    private val recyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        listPhoto.layoutManager = LinearLayoutManager(this)
        listPhoto.adapter = recyclerViewAdapter
        listPhoto.addOnItemTouchListener(RecyclerItemClickListener(this,  listPhoto, this))

        val url = createUri("https://api.flickr.com/services/feeds/photos_public.gne", "volvo,xc60", "en-US", true)
        val rawDataProvider = RawDataProvider(this)
        rawDataProvider.execute(url)
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult = sharedPreferences.getString(FLICKR_QUERY, "")

        if (queryResult != null && queryResult.isNotEmpty()) {
            val url = createUri("https://api.flickr.com/services/feeds/photos_public.gne", queryResult, "en-US", true)
            val rawDataProvider = RawDataProvider(this)
            rawDataProvider.execute(url)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadCompleted(data:  String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            val flickrDataParser = FlickrDataParser(this)
            flickrDataParser.execute(data)
        } else  {
            Log.d(TAG, status.name)
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG, data.toString())
        recyclerViewAdapter.loadNewData(data)
    }

    override fun onError(exception: Exception) {
        Log.e(TAG, exception.message.toString())
    }

    private fun createUri(baseURL: String, searchCriteria: String, lang: String, matchAll: Boolean): String {
        return Uri.parse(baseURL)
            .buildUpon()
            .appendQueryParameter("tags", searchCriteria)
            .appendQueryParameter("tagMode", if (matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang", lang)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .build()
            .toString()
    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, "item clicked")
        Toast.makeText(this,"Item clicked", Toast.LENGTH_LONG).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "item long clicked")
        val photo = recyclerViewAdapter.getPhoto(position)
        photo.let {
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }
}
