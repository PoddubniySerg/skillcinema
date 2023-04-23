package ru.skillbox.feature_film_page.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.core.domain.entities.MovieImage
import ru.skillbox.feature_film_page.R
import ru.skillbox.feature_film_page.databinding.FilmPageGalleryItemBinding

class GalleryItemAdapter(
    private val onItemClick: (MovieImage) -> Unit
) : ListAdapter<MovieImage, MovieImageViewHolder>(GalleryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImageViewHolder {
        return MovieImageViewHolder(
            FilmPageGalleryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieImageViewHolder, position: Int) {
        val gallery = getItem(position)
        with(holder.binding) {
            root.setOnClickListener { onItemClick(gallery) }
            Glide.with(imageView)
                .load(gallery.previewUrl)
                .placeholder(R.drawable.film_page_gallery_place_holder)
                .into(imageView)
        }
    }
}

class MovieImageViewHolder(
    val binding: FilmPageGalleryItemBinding
) : RecyclerView.ViewHolder(binding.root)

class GalleryDiffUtil : DiffUtil.ItemCallback<MovieImage>() {

    override fun areItemsTheSame(
        oldItem: MovieImage,
        newItem: MovieImage
    ): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(
        oldItem: MovieImage,
        newItem: MovieImage
    ): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }
}