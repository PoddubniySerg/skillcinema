package ru.skillbox.feature_list.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.skillbox.core.databinding.MoviesItemMovieBinding
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.feature_list.models.ListItem
import ru.skillbox.feature_list.ui.viewholders.BindViewHolder
import ru.skillbox.feature_list.ui.viewholders.MovieBindViewHolder

class ListPagingAdapter(
    private val type: Class<*>,
    private val onItemClick: (Any) -> Unit
) : PagingDataAdapter<ListItem, ViewHolder>(ListDiffUtilCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is BindViewHolder) {
            val item = getItem(position) ?: return
            holder.bind(item, onItemClick)
        } else {
            throw RuntimeException("Unknown view holder type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (type) {
            Movie::class.java -> return getMovieViewHolder(parent)
            else -> throw RuntimeException("Unknown list item type")
        }
    }

    private fun getMovieViewHolder(parent: ViewGroup): ViewHolder {
        return MovieBindViewHolder(
            MoviesItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class ListDiffUtilCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}