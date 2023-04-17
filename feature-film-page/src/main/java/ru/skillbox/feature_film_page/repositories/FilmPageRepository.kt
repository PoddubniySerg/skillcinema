package ru.skillbox.feature_film_page.repositories

import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.SerialSeason
import ru.skillbox.core.domain.entities.StaffItem

interface FilmPageRepository {

    suspend fun getFilmById(id: Long): MovieDetails

    suspend fun getFilmStaff(id: Long): List<StaffItem>

    suspend fun getSeriesSeasons(filmId: Long): List<SerialSeason>
}