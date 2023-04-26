package ru.skillbox.feature_film_page.repositories

import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.MovieImages
import ru.skillbox.core.domain.entities.RelatedMovies
import ru.skillbox.core.domain.entities.SerialSeason
import ru.skillbox.core.domain.entities.StaffItem

interface FilmPageRepository {

    suspend fun getFilmById(id: Long): MovieDetails

    suspend fun getFilmStaff(id: Long): List<StaffItem>

    suspend fun getSeriesSeasons(filmId: Long): List<SerialSeason>

    suspend fun getGallery(filmId: Long): MovieImages

    suspend fun getSimilars(filmId: Long): RelatedMovies

    suspend fun setFavourite(movie: MovieDetails): Boolean

    suspend fun getCollections(filmId: Long): List<String>
}