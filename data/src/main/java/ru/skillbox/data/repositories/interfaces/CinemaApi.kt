package ru.skillbox.data.repositories.interfaces

import ru.skillbox.core.domain.entities.CountriesAndGenres
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.PremierMovie
import java.time.Month

interface CinemaApi {

    suspend fun getCountriesAndGenres(): CountriesAndGenres

    suspend fun getPremiers(year: Int, month: Month, pageNumber: Int): List<PremierMovie>?

    suspend fun getMoviesByFilter(countryId: Int, genreId: Int, pageNumber: Int): List<MainMovie>

    suspend fun getPopular(pageNumber: Int): List<MainMovie>

    suspend fun getTop250(pageNumber: Int): List<MainMovie>

    suspend fun getTvSeries(pageNumber: Int): List<MainMovie>
}