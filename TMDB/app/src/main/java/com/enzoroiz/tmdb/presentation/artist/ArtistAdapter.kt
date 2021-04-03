package com.enzoroiz.tmdb.presentation.artist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enzoroiz.tmdb.BuildConfig
import com.enzoroiz.tmdb.data.model.artist.Artist
import com.enzoroiz.tmdb.databinding.ListItemBinding

class ArtistAdapter: RecyclerView.Adapter<ArtistViewHolder>() {
    private val items = mutableListOf<Artist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(updatedItems: List<Artist>) {
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }
}

class ArtistViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(artist: Artist) {
        val posterURL = "${BuildConfig.IMAGES_BASE_URL}${artist.profile_path}"
        Glide.with(binding.imageView).load(posterURL).into(binding.imageView)
        binding.txtListItemTitle.text = artist.name
        binding.txtListItemSubtitle.visibility = View.GONE
    }
}