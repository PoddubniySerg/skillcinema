package ru.skillbox.feature_list.domain

import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.entities.PremierMovie
import java.time.Month

interface MoviesRepository {

    suspend fun getPremiers(year: Int, month: Month, page: Int): List<PremierMovie>?

    suspend fun getPopular(pageNumber: Int): List<MainMovie>?

    suspend fun getSeries(pageNumber: Int): List<MainMovie>?

    suspend fun getTop250(pageNumber: Int): List<MainMovie>?

    suspend fun getMoviesByFilter(pageNumber: Int, countryId: Int, genreId: Int): List<MainMovie>?
}