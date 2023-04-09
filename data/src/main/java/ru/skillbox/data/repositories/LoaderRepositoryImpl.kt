package ru.skillbox.data.repositories

import ru.skillbox.core.domain.entities.CountriesAndGenres
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.data.repositories.interfaces.CinemaApi
import ru.skillbox.feature_onboarding.domain.repositories.LoaderRepository
import java.time.Month
import javax.inject.Inject

open class LoaderRepositoryImpl @Inject constructor(
    private val cinemaApi: CinemaApi
) : LoaderRepository {

    companion object {
        const val DEFAULT_PAGE_NUMBER = 1
    }

    override suspend fun getCountriesAndGenres(): CountriesAndGenres {
        return cinemaApi.getCountriesAndGenres()
    }

    override suspend fun getPremiers(year: Int, month: Month): List<PremierMovie>? {
        return cinemaApi.getPremiers(year, month, DEFAULT_PAGE_NUMBER)
    }

    override suspend fun getMoviesByFilter(countryId: Int, genreId: Int): List<MainMovie> {
        return cinemaApi.getMoviesByFilter(countryId, genreId, DEFAULT_PAGE_NUMBER)
    }

    override suspend fun getPopular(): List<MainMovie> {
        return cinemaApi.getPopular(DEFAULT_PAGE_NUMBER)
    }

    override suspend fun getTop250(): List<MainMovie> {
        return cinemaApi.getTop250(DEFAULT_PAGE_NUMBER)
    }

    override suspend fun getSeries(): List<MainMovie> {
        return cinemaApi.getTvSeries(DEFAULT_PAGE_NUMBER)
    }
}