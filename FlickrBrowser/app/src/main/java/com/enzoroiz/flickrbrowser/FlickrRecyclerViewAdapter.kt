package com.enzoroiz.flickrbrowser

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FlickrRecyclerViewAdapter(private var photoList: List<Photo>) :
    RecyclerView.Adapter<FlickrImageViewHolder>() {
    private val TAG = "RecyclerViewAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        // Called by the Layout Manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return FlickrImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        // Called bu the layout manager when it wants new data in an existing view
        Log.d(TAG, "onBindViewHolder - $position")

        if (photoList.isEmpty()) {
            holder.thumbnail.setImageResource(R.drawable.ic_placeholder)
            holder.title.setText(R.string.empty_photo_list)
        } else {
            val listItem = photoList[position]
            Picasso.get()
                .load(listItem.image)
                .error(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.thumbnail)

            holder.title.text = listItem.title
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return if (photoList.isNotEmpty()) photoList.size else 1
    }

    fun loadNewData(newPhotosList: List<Photo>) {
        photoList = newPhotosList
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): Photo? {
        return if (photoList.isNotEmpty()) photoList[position] else null
    }

}

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail: ImageView = view.findViewById(R.id.img_thumbnail)
    var title: TextView = view.findViewById(R.id.txt_title)
}

class RecyclerItemClickListener(
    context: Context,
    recyclerView: RecyclerView,
    private val listener: OnRecyclerClickListener
) : RecyclerView.SimpleOnItemTouchListener() {
    private val TAG = "RecyclerClickListener"
    private val gestureDetector = GestureDetectorCompat(context, object: GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG, "onSingleTapUp")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onSingleTapUp calling listener")
            childView?.let {
                listener.onItemClick(it, recyclerView.getChildAdapterPosition(childView))
                return true
            }

            return false
        }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG, "onLongPress")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onLongPress calling listener")
            childView?.let {
                listener.onItemLongClick(it, recyclerView.getChildAdapterPosition(childView))
            }
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, "touch Event: $e")
        val result = gestureDetector.onTouchEvent(e)
        Log.d(TAG, "onInterceptTouchEvent returning: $result")
        return super.onInterceptTouchEvent(rv, e)
    }

    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }
}