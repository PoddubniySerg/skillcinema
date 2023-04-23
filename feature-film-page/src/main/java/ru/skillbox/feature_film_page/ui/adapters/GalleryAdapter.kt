package ru.skillbox.feature_film_page.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.domain.entities.MovieImage
import ru.skillbox.feature_film_page.models.MovieImagesGallery
import ru.skillbox.feature_film_page.ui.viewholders.GalleryViewHolder

class GalleryAdapter(
    private val title: String,
    private val onClickAllButton: (MovieImagesGallery) -> Unit,
    private val onItemClick: (MovieImage) -> Unit
) : RecyclerView.Adapter<GalleryViewHolder>() {

    private var gallery: MovieImagesGallery? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryViewHolder {
        val binding =
            CollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(title, onClickAllButton, onItemClick, binding)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        if (gallery == null) {
            return
        }
        holder.bind(gallery!!)
    }

    fun setGallery(gallery: MovieImagesGallery) {
        this.gallery = gallery
    }
}