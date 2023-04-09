package ru.skillbox.core.utils

import ru.skillbox.core.domain.entities.GenreString
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.core.dto.MovieDto
import ru.skillbox.core.dto.PremierDto

object Converter {

    fun convertPremiers(premiers: PremierMovie): PremierDto {
        val genres = arrayListOf<GenreString>()
        premiers.genres?.forEach { genres.add(it) }
        return PremierDto(
            premiers.id,
            premiers.nameRu,
            premiers.nameEn,
            premiers.posterUrl,
            premiers.posterUrlPreview,
            premiers.seen,
            genres,
            premiers.premiereRu
        )
    }

    fun convertMovies(movies: MainMovie): MovieDto {
        val genres = arrayListOf<GenreString>()
        movies.genres?.forEach { genres.add(it) }
        return MovieDto(
            movies.genres,
            movies.rating,
            movies.id,
            movies.nameRu,
            movies.nameEn,
            movies.posterUrl,
            movies.posterUrlPreview,
            movies.seen
        )
    }
}