package com.enzoroiz.flickrbrowser

import android.os.AsyncTask
import org.json.JSONObject
import java.lang.Exception

class FlickrDataParser(private val listener: OnDataAvailable) :
    AsyncTask<String, Void, ArrayList<Photo>>() {

    override fun onPostExecute(result: ArrayList<Photo>) {
        listener.onDataAvailable(result)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        val photoList = arrayListOf<Photo>()

        try {
            val jsonItemsData = JSONObject(params[0].toString())
            val items = jsonItemsData.getJSONArray("items")
            for (i in 0 until items.length()) {
                val jsonPhotoData = items.getJSONObject(i)
                val title = jsonPhotoData.getString("title")
                val author = jsonPhotoData.getString("author")
                val authorId = jsonPhotoData.getString("author_id")
                val tags = jsonPhotoData.getString("tags")

                val photoURL = jsonPhotoData.getJSONObject("media").getString("m")
                val link = photoURL.replaceFirst("_m.jpg", "_b.jpg")
                val photo = Photo(title, author, authorId, link, tags, photoURL)
                photoList.add(photo)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            cancel(true)
            listener.onError(e)
        }

        return photoList
    }

    interface OnDataAvailable {
        fun onDataAvailable(data: List<Photo>)
        fun onError(exception: Exception)
    }
}