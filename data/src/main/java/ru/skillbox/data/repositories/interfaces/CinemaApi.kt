package ru.skillbox.data.repositories.interfaces

import ru.skillbox.core.domain.entities.*
import java.time.Month

interface CinemaApi {

    suspend fun getCountriesAndGenres(): CountriesAndGenres

    suspend fun getPremiers(year: Int, month: Month, pageNumber: Int): List<PremierMovie>?

    suspend fun getMoviesByFilter(countryId: Int, genreId: Int, pageNumber: Int): List<MainMovie>

    suspend fun getPopular(pageNumber: Int): List<MainMovie>

    suspend fun getTop250(pageNumber: Int): List<MainMovie>

    suspend fun getTvSeries(pageNumber: Int): List<MainMovie>

    suspend fun getFilmById(id: Long): MovieDetails

    suspend fun getFilmStaff(id: Long): List<StaffItem>

    suspend fun getSeriesSeasons(filmId: Long): List<SerialSeason>

    suspend fun getGallery(filmId: Long, page: Int): MovieImages

    suspend fun getRelatedMovies(filmId: Long): RelatedMovies
}