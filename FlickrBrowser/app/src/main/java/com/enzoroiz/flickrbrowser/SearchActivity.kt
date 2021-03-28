package com.enzoroiz.flickrbrowser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.preference.PreferenceManager

import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), SearchView.OnCloseListener, SearchView.OnQueryTextListener {
    private val TAG = "SearchActivity"
    private var searchView: SearchView? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView = (menu.findItem(R.id.app_bar_search).actionView as SearchView).also {
            it.setSearchableInfo(searchableInfo)
            it.setOnQueryTextListener(this)
            it.setOnCloseListener(this)
            it.isIconified = false
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_search)
        activateToolbar(true)
    }

    override fun onClose(): Boolean {
        finish()
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedPreferences.edit().putString(FLICKR_QUERY, query).apply()
        searchView?.clearFocus()

        finish()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}
