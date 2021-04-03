package com.enzoroiz.tmdb.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enzoroiz.tmdb.BuildConfig
import com.enzoroiz.tmdb.data.model.movie.Movie
import com.enzoroiz.tmdb.databinding.ListItemBinding

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {
    private val items = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(updatedItems: List<Movie>) {
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }
}

class MovieViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) {
        val posterURL = "${BuildConfig.IMAGES_BASE_URL}${movie.poster_path}"
        Glide.with(binding.imageView).load(posterURL).into(binding.imageView)
        binding.txtListItemTitle.text = movie.title
        binding.txtListItemSubtitle.text = movie.overview
    }
}