package ru.skillbox.feature_film_page.ui.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.ui.adapters.MovieItemAdapter
import ru.skillbox.core.ui.viewholders.CollectionViewHolder
import ru.skillbox.feature_film_page.R
import ru.skillbox.feature_film_page.models.TranslatableMoviesCollection
import ru.skillbox.feature_film_page.models.TranslatableRelatedMovie

class RelatedMoviesViewHolder(
    private val title: String,
    private val onItemClick: (Movie) -> Unit,
    private val onClickAllButton: (TranslatableMoviesCollection) -> Unit,
    binding: CollectionBinding
) : CollectionViewHolder<TranslatableMoviesCollection>(
    binding,
    dpListTopMargin = 24,
    dpRootTopMargin = 40
) {

    private val maxItemsViewed: Int = 20
    private lateinit var adapter: MovieItemAdapter

    init {
        with(binding) {
            nameCollection.text = title
            initAdapter(collection)
            initLayoutManager(collection)
        }
    }

    override fun bind(item: TranslatableMoviesCollection) {
        val limitedItems = item.movies.take(maxItemsViewed)
        with(binding) {
            setTextAllButton(buttonShowAll, item.movies)
            buttonShowAll.setOnClickListener { onClickAllButton(item) }
            adapter.submitList(limitedItems)
        }
    }

    private fun initAdapter(collectionView: RecyclerView) {
        adapter =
            MovieItemAdapter(
                allButtonIcon = null,
                allButtonText = null,
                onItemClick
            )
        collectionView.adapter = adapter
    }

    private fun initLayoutManager(collectionView: RecyclerView) {
        val manager = LinearLayoutManager(collectionView.context, RecyclerView.HORIZONTAL, false)
        collectionView.layoutManager = manager
    }

    private fun setTextAllButton(button: AppCompatButton, items: List<TranslatableRelatedMovie>) {
        if (items.size < maxItemsViewed) {
            button.visibility = View.GONE
            return
        }
        button.visibility = View.VISIBLE
        val iconId = R.drawable.film_page_show_all_button_icon
        val icon = ResourcesCompat.getDrawable(button.resources, iconId, null)
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
        button.text = items.size.toString()
    }
}