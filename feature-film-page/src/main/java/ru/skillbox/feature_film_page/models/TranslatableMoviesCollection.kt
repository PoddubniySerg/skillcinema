package ru.skillbox.feature_film_page.models

import ru.skillbox.core.domain.entities.RelatedMovies

class TranslatableMoviesCollection(
    override val total: Int,
    override val movies: List<TranslatableRelatedMovie>
) : RelatedMovies