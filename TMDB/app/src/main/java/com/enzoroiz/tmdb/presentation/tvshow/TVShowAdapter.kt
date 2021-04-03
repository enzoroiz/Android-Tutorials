package com.enzoroiz.tmdb.presentation.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enzoroiz.tmdb.BuildConfig
import com.enzoroiz.tmdb.data.model.tvshow.TVShow
import com.enzoroiz.tmdb.databinding.ListItemBinding

class TVShowAdapter : RecyclerView.Adapter<TVShowViewHolder>() {
    private val items = mutableListOf<TVShow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(updatedItems: List<TVShow>) {
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }
}

class TVShowViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tvShow: TVShow) {
        val posterURL = "${BuildConfig.IMAGES_BASE_URL}${tvShow.poster_path}"
        Glide.with(binding.imageView).load(posterURL).into(binding.imageView)
        binding.txtListItemTitle.text = tvShow.name
        binding.txtListItemSubtitle.text = tvShow.overview
    }
}