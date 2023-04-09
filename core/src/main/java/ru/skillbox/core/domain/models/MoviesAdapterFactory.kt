package ru.skillbox.core.domain.models

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.domain.entities.AdapterFactory
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.ui.adapters.MovieItemAdapter

class MoviesAdapterFactory(
    private val onItemPosterClick: (Movie) -> Unit
) : AdapterFactory<Movie, RecyclerView.ViewHolder> {

    override fun getAdapter(onClickAllButton: () -> Unit): ListAdapter<Movie, RecyclerView.ViewHolder> {
        return MovieItemAdapter(onItemPosterClick, onClickAllButton)
    }
}