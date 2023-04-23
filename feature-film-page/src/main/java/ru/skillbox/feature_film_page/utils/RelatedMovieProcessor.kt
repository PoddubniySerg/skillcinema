package ru.skillbox.feature_film_page.utils

import ru.skillbox.core.domain.entities.RelatedMovie
import ru.skillbox.core.domain.entities.RelatedMovies
import ru.skillbox.feature_film_page.models.TranslatableMoviesCollection
import ru.skillbox.feature_film_page.models.TranslatableRelatedMovie

object RelatedMovieProcessor {

    fun convertToTranslatable(collection: RelatedMovies): TranslatableMoviesCollection {
        return TranslatableMoviesCollection(
            collection.total,
            collection.movies.map { convertToTranslatableMovie(it) }
        )
    }

    private fun convertToTranslatableMovie(relatedMovie: RelatedMovie): TranslatableRelatedMovie {
        return TranslatableRelatedMovie(
            relatedMovie.id,
            relatedMovie.nameRu,
            relatedMovie.nameEn,
            relatedMovie.posterUrl,
            relatedMovie.posterUrlPreview,
            relatedMovie.seen,
            relatedMovie.nameOriginal
        )
    }
}