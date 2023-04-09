package ru.skillbox.feature_list.utils

import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.feature_list.models.MovieItem

object Converter {

    fun convert(movie: MainMovie): MovieItem {
        return MovieItem(
            movie.genres,
            movie.rating,
            movie.id,
            movie.nameRu,
            movie.nameEn,
            movie.posterUrl,
            movie.posterUrlPreview,
            movie.seen
        )
    }

    fun convert(movie: PremierMovie): MovieItem {
        return MovieItem(
            movie.genres,
            rating = null,
            movie.id,
            movie.nameRu,
            movie.nameEn,
            movie.posterUrl,
            movie.posterUrlPreview,
            movie.seen
        )
    }
}