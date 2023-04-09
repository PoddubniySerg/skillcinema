package ru.skillbox.core.domain.repositories

import ru.skillbox.core.domain.entities.CountriesAndGenres
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.PremierMovie
import java.time.Month

interface MoviesCollectionsSource {

    suspend fun getCountriesAndGenres(): CountriesAndGenres

    suspend fun getPremiers(year: Int, month: Month): List<PremierMovie>?

    suspend fun getMoviesByFilter(countryId: Int, genreId: Int): List<MainMovie>?

    suspend fun getPopular(): List<MainMovie>

    suspend fun getTop250(): List<MainMovie>

    suspend fun getSeries(): List<MainMovie>
}