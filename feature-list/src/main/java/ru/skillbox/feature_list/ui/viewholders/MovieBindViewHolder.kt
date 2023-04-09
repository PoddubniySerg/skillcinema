package ru.skillbox.feature_list.ui.viewholders

import ru.skillbox.core.databinding.MoviesItemMovieBinding
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.ui.viewholders.MovieViewHolder

class MovieBindViewHolder(
    binding: MoviesItemMovieBinding
) : MovieViewHolder(binding), BindViewHolder {

    override fun bind(any: Any, onItemClick: (Any) -> Unit) {
        if (any is Movie) {
            super.bind(any, onItemClick)
        }
    }
}