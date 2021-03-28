package com.enzoroiz.top10downloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FeedAdapter(
    context: Context,
    private val layoutRes: Int,
    private var applications: List<FeedEntry>
) : ArrayAdapter<FeedEntry>(context, layoutRes, applications) {
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        
        if (convertView == null) {
            view = inflater.inflate(layoutRes, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentApplication = applications[position]

        viewHolder.txtName.text = currentApplication.name
        viewHolder.txtArtist.text = currentApplication.artist
        viewHolder.txtSummary.text = currentApplication.summary

        return view
    }

    override fun getCount(): Int {
        return applications.size
    }

    fun setFeedList(feedList: List<FeedEntry>) {
        applications = feedList
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) {
    val txtName: TextView = view.findViewById(R.id.txtName)
    val txtArtist: TextView = view.findViewById(R.id.txtArtist)
    val txtSummary: TextView = view.findViewById(R.id.txtSummary)
}