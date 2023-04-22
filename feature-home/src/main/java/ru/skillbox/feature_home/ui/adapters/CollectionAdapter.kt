package ru.skillbox.feature_home.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.models.MoviesCollection
import ru.skillbox.core.ui.adapters.MovieItemAdapter
import ru.skillbox.feature_home.ui.viewholders.MoviesCollectionViewHolder

class CollectionAdapter(
    private val showAllButtonIcon: Drawable,
    private val showAllItemText: String,
    private val onItemClick: (Movie) -> Unit,
    private val allButtonText: String,
    private val dividerItemDecorations: List<DividerItemDecoration>,
    private val onClickAllButton: (MoviesCollection) -> Unit
) : ListAdapter<MoviesCollection, MoviesCollectionViewHolder>(MoviesCollectionDiffUtil()) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesCollectionViewHolder {
        val binding =
            CollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val adapter =
            MovieItemAdapter(showAllButtonIcon, showAllItemText, onItemClick) { onClickAllButton }
        return MoviesCollectionViewHolder(
            adapter,
            onClickAllButton,
            allButtonText,
            dividerItemDecorations,
            viewPool,
            binding
        )
    }

    override fun onBindViewHolder(holder: MoviesCollectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class MoviesCollectionDiffUtil : DiffUtil.ItemCallback<MoviesCollection>() {

    override fun areItemsTheSame(oldItem: MoviesCollection, newItem: MoviesCollection): Boolean {
        return oldItem.index == newItem.index
    }

    override fun areContentsTheSame(oldItem: MoviesCollection, newItem: MoviesCollection): Boolean {
        return oldItem == newItem
    }

}