package ru.skillbox.core.domain.models

import ru.skillbox.core.domain.entities.Movie

data class MoviesCollection(
    val index: Int,
    val title: String,
    val filter: MoviesFilter?,
    val movies: List<Movie>
)
