package ru.skillbox.feature_film_page.ui.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.domain.entities.MovieImage
import ru.skillbox.core.ui.viewholders.CollectionViewHolder
import ru.skillbox.feature_film_page.R
import ru.skillbox.feature_film_page.models.MovieImagesGallery
import ru.skillbox.feature_film_page.ui.adapters.GalleryItemAdapter

class GalleryViewHolder(
    private val title: String,
    private val onClickAllButton: (MovieImagesGallery) -> Unit,
    private val onItemClick: (MovieImage) -> Unit,
    binding: CollectionBinding
) : CollectionViewHolder<MovieImagesGallery>(binding, dpListTopMargin = 16, dpRootTopMargin = 40) {

    private lateinit var adapter: GalleryItemAdapter
    private val maxItemsViewed = 20

    init {
        with(binding) {
            nameCollection.text = title
            setManager(collection)
            setAdapter(collection)
        }
    }

    override fun bind(item: MovieImagesGallery) {
        val limitedItems = item.gallery.images?.take(maxItemsViewed)
        with(binding) {
            setTextAllButton(buttonShowAll, item.gallery.total)
            buttonShowAll.setOnClickListener { onClickAllButton(item) }
            adapter.submitList(limitedItems)
        }
    }

    private fun setManager(collectionItems: RecyclerView) {
        val manager =
            LinearLayoutManager(collectionItems.context, RecyclerView.HORIZONTAL, false)
        collectionItems.layoutManager = manager
    }

    private fun setAdapter(collectionItems: RecyclerView) {
        adapter = GalleryItemAdapter(onItemClick)
        collectionItems.adapter = adapter
    }

    private fun setTextAllButton(button: AppCompatButton, total: Int) {
        if (total < maxItemsViewed) {
            button.visibility = View.GONE
            return
        }
        button.visibility = View.VISIBLE
        val iconId = R.drawable.film_page_show_all_button_icon
        val icon = ResourcesCompat.getDrawable(button.resources, iconId, null)
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
        button.text = total.toString()
    }
}