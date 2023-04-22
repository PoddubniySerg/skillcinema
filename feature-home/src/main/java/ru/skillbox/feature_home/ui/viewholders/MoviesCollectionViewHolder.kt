package ru.skillbox.feature_home.ui.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.domain.models.MoviesCollection
import ru.skillbox.core.ui.adapters.MovieItemAdapter
import ru.skillbox.core.ui.viewholders.CollectionViewHolder
import ru.skillbox.core.utils.Constants

class MoviesCollectionViewHolder(
    private val adapter: MovieItemAdapter,
    private val onClickAllButton: (MoviesCollection) -> Unit,
    private val allButtonText: String,
    private val dividerItemDecorations: List<DividerItemDecoration>,
    private val viewPool: RecyclerView.RecycledViewPool,
    binding: CollectionBinding
) : CollectionViewHolder<MoviesCollection>(binding, dpListTopMargin = 24, dpRootTopMargin = 36) {

    init {
        with(binding) {
            collection.layoutManager = LinearLayoutManager(
                collection.context,
                RecyclerView.HORIZONTAL,
                false
            )
            collection.adapter = adapter
            collection.setRecycledViewPool(viewPool)
            dividerItemDecorations.forEach { collection.addItemDecoration(it) }
        }
    }

    override fun bind(item: MoviesCollection) {
        with(binding) {
            nameCollection.text = item.title
            setTextAllButton(buttonShowAll, item.movies.size)
            adapter.submitList(item.movies)
            buttonShowAll.setOnClickListener { onClickAllButton(item) }
        }
    }

    private fun setTextAllButton(buttonShowAll: AppCompatButton, size: Int) {
        if (size < Constants.MAX_MOVIES_COLLECTION_SIZE) {
            buttonShowAll.visibility = View.GONE
        } else {
            buttonShowAll.text = allButtonText
        }
    }
}