package ru.skillbox.core.domain.models

data class AppCollection<I>(
    val index: Int,
    val title: String,
    val filter: MoviesFilter?,
    val movies: List<I>
)
