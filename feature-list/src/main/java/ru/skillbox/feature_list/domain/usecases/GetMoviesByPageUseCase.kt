package ru.skillbox.feature_list.domain.usecases

import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.core.domain.models.MoviesCollectionTypes
import ru.skillbox.core.domain.models.MoviesFilter
import ru.skillbox.core.utils.DatesSource
import ru.skillbox.core.utils.MoviesUtils
import ru.skillbox.feature_list.domain.repositories.MoviesRepository
import ru.skillbox.feature_list.models.MovieItem
import ru.skillbox.feature_list.utils.Converter
import javax.inject.Inject

class GetMoviesByPageUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend fun execute(
        typeIndex: MoviesCollectionTypes?,
        pageNumber: Int,
        filter: MoviesFilter?
    ): List<MovieItem> {
        return when (typeIndex) {
            MoviesCollectionTypes.PREMIERS -> getPremiers(pageNumber)
            MoviesCollectionTypes.POPULAR -> getPopular(pageNumber)
            MoviesCollectionTypes.TOP250 -> getTop250(pageNumber)
            MoviesCollectionTypes.TV_SERIES -> getTvSeries(pageNumber)
            else -> getFilteredMovies(pageNumber, filter!!)
        }
    }

    private suspend fun getPremiers(pageNumber: Int): List<MovieItem> {
        val dateFrom = DatesSource.getDateFrom()
        val dateTo = DatesSource.getDateTo(dateFrom)
        val formatter = DatesSource.getDateFormatter()
        val movies = mutableListOf<PremierMovie>()
        moviesRepository.getPremiers(dateFrom.year, dateFrom.month, pageNumber)?.let { movieList ->
            movies.addAll(movieList)
        }
        if (dateFrom.month != dateTo.month) {
            moviesRepository.getPremiers(dateTo.year, dateTo.month, pageNumber)?.let { movieList ->
                movies.addAll(movieList)
            }
        }
        return MoviesUtils
            .filterPremiersByDate(movies, dateFrom, dateTo, formatter)
            .map { Converter.convert(it) }
    }

    private suspend fun getPopular(pageNumber: Int): List<MovieItem> {
        return moviesRepository.getPopular(pageNumber)?.map { Converter.convert(it) } ?: emptyList()
    }

    private suspend fun getTop250(pageNumber: Int): List<MovieItem> {
        return moviesRepository.getTop250(pageNumber)?.map { Converter.convert(it) } ?: emptyList()
    }

    private suspend fun getTvSeries(pageNumber: Int): List<MovieItem> {
        return moviesRepository.getSeries(pageNumber)?.map { Converter.convert(it) } ?: emptyList()
    }

    private suspend fun getFilteredMovies(pageNumber: Int, filter: MoviesFilter): List<MovieItem> {
        val countryId = filter.countryId
        val genreId = filter.genreId
        return moviesRepository
            .getMoviesByFilter(pageNumber, countryId, genreId)
            ?.map { Converter.convert(it) } ?: emptyList()
    }
}