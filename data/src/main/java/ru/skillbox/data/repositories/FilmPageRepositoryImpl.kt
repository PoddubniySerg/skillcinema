package ru.skillbox.data.repositories

import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.SerialSeason
import ru.skillbox.core.domain.entities.StaffItem
import ru.skillbox.data.repositories.interfaces.CinemaApi
import ru.skillbox.feature_film_page.repositories.FilmPageRepository
import javax.inject.Inject

class FilmPageRepositoryImpl @Inject constructor(
    private val cinemaApi: CinemaApi
) : FilmPageRepository {

    override suspend fun getFilmById(id: Long): MovieDetails {
        return cinemaApi.getFilmById(id)
    }

    override suspend fun getFilmStaff(id: Long): List<StaffItem> {
        return cinemaApi.getFilmStaff(id)
    }

    override suspend fun getSeriesSeasons(filmId: Long): List<SerialSeason> {
        return cinemaApi.getSeriesSeasons(filmId)
    }


}