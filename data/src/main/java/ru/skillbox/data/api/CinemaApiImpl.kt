package ru.skillbox.data.api

import ru.skillbox.core.domain.entities.CountriesAndGenres
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.data.exceptions.NetworkResponseException
import ru.skillbox.data.repositories.interfaces.CinemaApi
import java.time.Month
import javax.inject.Inject

class CinemaApiImpl @Inject constructor(
    private val source: KinopoiskApi
) : CinemaApi {

    override suspend fun getCountriesAndGenres(): CountriesAndGenres {
        val response = source.getCountriesAndGenres()
        return response.body()
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getPremiers(year: Int, month: Month, pageNumber: Int): List<PremierMovie>? {
        val maxPremiersPages = 1
        if (pageNumber > maxPremiersPages) {
            return null
        }
        val response = source.getPremiers(year, month.name)
        return response.body()?.items
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getMoviesByFilter(
        countryId: Int,
        genreId: Int,
        pageNumber: Int
    ): List<MainMovie> {
        val response = source.getByFilters(countryId, genreId, pageNumber)
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getPopular(pageNumber: Int): List<MainMovie> {
        val response = source.getPopular(pageNumber)
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getTop250(pageNumber: Int): List<MainMovie> {
        val response = source.getTop250(pageNumber)
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getTvSeries(pageNumber: Int): List<MainMovie> {
        val response = source.getTvSeries(pageNumber)
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }
}