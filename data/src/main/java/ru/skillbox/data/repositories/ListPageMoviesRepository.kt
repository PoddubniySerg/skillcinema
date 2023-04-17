package ru.skillbox.data.repositories

import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.data.repositories.interfaces.CinemaApi
import ru.skillbox.feature_list.domain.MoviesRepository
import java.time.Month
import javax.inject.Inject

class ListPageMoviesRepository @Inject constructor(
    private val cinemaApi: CinemaApi
) : MoviesRepository {

    override suspend fun getPremiers(year: Int, month: Month, page: Int): List<PremierMovie>? {
        return cinemaApi.getPremiers(year, month, page)
    }

    override suspend fun getPopular(pageNumber: Int): List<MainMovie>? {
        return cinemaApi.getPopular(pageNumber)
    }

    override suspend fun getSeries(pageNumber: Int): List<MainMovie>? {
        return cinemaApi.getTvSeries(pageNumber)
    }

    override suspend fun getTop250(pageNumber: Int): List<MainMovie>? {
        return cinemaApi.getTop250(pageNumber)
    }

    override suspend fun getMoviesByFilter(
        pageNumber: Int,
        countryId: Int,
        genreId: Int
    ): List<MainMovie>? {
        return cinemaApi.getMoviesByFilter(countryId, genreId, pageNumber)
    }
}