package ru.skillbox.feature_home.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.models.MoviesCollection
import ru.skillbox.core.ui.adapters.MovieItemAdapter
import ru.skillbox.core.utils.Constants

class CollectionAdapter(
    private val showAllButtonIcon: Drawable,
    private val showAllItemText: String,
    private val onItemClick: (Movie) -> Unit,
    private val allButtonText: String,
    private val dividerItemDecorations: List<DividerItemDecoration>,
    private val onClickAllButton: (MoviesCollection) -> Unit
) : ListAdapter<MoviesCollection, CollectionViewHolder>(MoviesCollectionDiffUtil()) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(
            CollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = getItem(position)
        val adapter =
            MovieItemAdapter(showAllButtonIcon, showAllItemText, onItemClick) { onClickAllButton(item) }
        with(holder.binding) {
            nameCollection.text = item.title
            setTextAllButton(buttonShowAll, item.movies.size)
            collection.adapter = adapter
            collection.layoutManager = LinearLayoutManager(
                collection.context,
                RecyclerView.HORIZONTAL,
                false
            )
            collection.setRecycledViewPool(viewPool)
            dividerItemDecorations.forEach { collection.addItemDecoration(it) }
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

class CollectionViewHolder(val binding: CollectionBinding) : ViewHolder(binding.root)

class MoviesCollectionDiffUtil : DiffUtil.ItemCallback<MoviesCollection>() {

    override fun areItemsTheSame(oldItem: MoviesCollection, newItem: MoviesCollection): Boolean {
        return oldItem.index == newItem.index
    }

    override fun areContentsTheSame(oldItem: MoviesCollection, newItem: MoviesCollection): Boolean {
        return oldItem == newItem
    }

}