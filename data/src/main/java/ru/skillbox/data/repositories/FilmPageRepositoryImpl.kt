package ru.skillbox.data.repositories

import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.MovieImages
import ru.skillbox.core.domain.entities.RelatedMovies
import ru.skillbox.core.domain.entities.SerialSeason
import ru.skillbox.core.domain.entities.StaffItem
import ru.skillbox.data.repositories.interfaces.CinemaApi
import ru.skillbox.data.repositories.interfaces.DeviceDao
import ru.skillbox.feature_film_page.repositories.FilmPageRepository
import javax.inject.Inject

class FilmPageRepositoryImpl @Inject constructor(
    private val cinemaApi: CinemaApi,
    private val deviceDao: DeviceDao
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

    override suspend fun getGallery(filmId: Long): MovieImages {
        return cinemaApi.getGallery(filmId, page = 1)
    }

    override suspend fun getSimilars(filmId: Long): RelatedMovies {
        return cinemaApi.getRelatedMovies(filmId)
    }

    override suspend fun setFavourite(movie: MovieDetails): Boolean {
        return deviceDao.setFavourite(movie)
    }

    override suspend fun setWillView(movie: MovieDetails): Boolean {
        return deviceDao.setWillView(movie)
    }

    override suspend fun setViewed(movie: MovieDetails): Boolean {
        return deviceDao.setViewed(movie)
    }

    override suspend fun getCollections(filmId: Long): List<String> {
        return deviceDao.getCollections(filmId)
    }
}