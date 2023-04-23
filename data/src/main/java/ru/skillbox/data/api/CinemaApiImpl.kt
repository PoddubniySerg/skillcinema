package ru.skillbox.data.api

import retrofit2.Response
import ru.skillbox.core.domain.entities.CountriesAndGenres
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.MovieImages
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.core.domain.entities.RelatedMovies
import ru.skillbox.core.domain.entities.SerialSeason
import ru.skillbox.core.domain.entities.StaffItem
import ru.skillbox.data.exceptions.NetworkResponseException
import ru.skillbox.data.repositories.interfaces.CinemaApi
import java.time.Month
import javax.inject.Inject

class CinemaApiImpl @Inject constructor(
    private val source: KinopoiskApi
) : CinemaApi {

    override suspend fun getCountriesAndGenres(): CountriesAndGenres {
        val response = source.getCountriesAndGenres()
        return response.body() ?: throwNetworkResponseException(response)
    }

    override suspend fun getPremiers(
        year: Int,
        month: Month,
        pageNumber: Int
    ): List<PremierMovie>? {
        val maxPremiersPages = 1
        if (pageNumber > maxPremiersPages) {
            return null
        }
        val response = source.getPremiers(year, month.name)
        return response.body()?.items ?: throwNetworkResponseException(response)
    }

    override suspend fun getMoviesByFilter(
        countryId: Int,
        genreId: Int,
        pageNumber: Int
    ): List<MainMovie> {
        val response = source.getByFilters(countryId, genreId, pageNumber)
        return response.body()?.films ?: throwNetworkResponseException(response)
    }

    override suspend fun getPopular(pageNumber: Int): List<MainMovie> {
        val response = source.getPopular(pageNumber)
        return response.body()?.films ?: throwNetworkResponseException(response)
    }

    override suspend fun getTop250(pageNumber: Int): List<MainMovie> {
        val response = source.getTop250(pageNumber)
        return response.body()?.films ?: throwNetworkResponseException(response)
    }

    override suspend fun getTvSeries(pageNumber: Int): List<MainMovie> {
        val response = source.getTvSeries(pageNumber)
        return response.body()?.films ?: throwNetworkResponseException(response)
    }

    override suspend fun getFilmById(id: Long): MovieDetails {
        val response = source.getMovieDetails(id)
        return response.body() ?: throwNetworkResponseException(response)
    }

    override suspend fun getFilmStaff(id: Long): List<StaffItem> {
        val response = source.getMovieStaff(id)
        return response.body() ?: emptyList()
    }

    override suspend fun getSeriesSeasons(filmId: Long): List<SerialSeason> {
        val response = source.getSeasons(filmId.toInt())
        return response.body()?.items ?: throwNetworkResponseException(response)
    }

    override suspend fun getGallery(filmId: Long, page: Int): MovieImages {
        val response = source.getGallery(filmId.toInt(), page)
        return response.body() ?: throwNetworkResponseException(response)
    }

    override suspend fun getRelatedMovies(filmId: Long): RelatedMovies {
        val response = source.getSimilars(filmId.toInt())
        return response.body() ?: throwNetworkResponseException(response)
    }

    private fun throwNetworkResponseException(response: Response<*>): Nothing {
        throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }
}