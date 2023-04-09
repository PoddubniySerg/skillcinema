package ru.skillbox.core.domain.usecases

import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.core.domain.models.AppCollection
import ru.skillbox.core.domain.models.MoviesCollectionTypes
import ru.skillbox.core.domain.models.MoviesFilter
import ru.skillbox.core.domain.repositories.MoviesCollectionsSource
import ru.skillbox.core.utils.Constants
import ru.skillbox.core.utils.CountryAndGenreSource
import ru.skillbox.core.utils.DatesSource
import ru.skillbox.core.utils.MoviesUtils
import kotlin.streams.toList

open class GetMoviesCollectionsUseCase(
    private val collectionsSource: MoviesCollectionsSource
) {

    suspend fun execute(names: List<String>): List<AppCollection<Movie>> {
        val collections = mutableListOf<AppCollection<Movie>>()
        for (i in names.indices) {
            when (i) {
                MoviesCollectionTypes.PREMIERS.index -> collections.add(getPremiers(names[i], i))
                MoviesCollectionTypes.POPULAR.index -> collections.add(getPopular(names[i], i))
                MoviesCollectionTypes.TOP250.index -> collections.add(getTop250(names[i], i))
                MoviesCollectionTypes.TV_SERIES.index -> collections.add(getTvSeries(names[i], i))
                else -> collections.add(getRandomCollection(i))
            }
        }
        return collections
    }

    private suspend fun getPremiers(name: String, index: Int): AppCollection<Movie> {
        val dateFrom = DatesSource.getDateFrom()
        val dateTo = DatesSource.getDateTo(dateFrom)
        val formatter = DatesSource.getDateFormatter()
        val movies = mutableListOf<PremierMovie>()
        collectionsSource.getPremiers(dateFrom.year, dateFrom.month)?.let { movies.addAll(it) }
        if (dateFrom.month != dateTo.month) {
            collectionsSource.getPremiers(dateTo.year, dateTo.month)?.let { movies.addAll(it) }
        }
        val premiers = MoviesUtils.filterPremiersByDate(movies, dateFrom, dateTo, formatter)
        return AppCollection(index, name, filter = null, premiers)
    }

    private suspend fun getPopular(name: String, index: Int): AppCollection<Movie> {
        val movies = collectionsSource.getPopular().stream()
            .limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
            .toList()
        return AppCollection(index, name, filter = null, movies)
    }

    private suspend fun getRandomCollection(index: Int): AppCollection<Movie> {
        val countryAndGenre = CountryAndGenreSource(collectionsSource.getCountriesAndGenres())
        val name = "${countryAndGenre.genreName} ${countryAndGenre.countryName}"
        val filter = MoviesFilter(countryAndGenre.countryId, countryAndGenre.genreId)
        val movies =
            collectionsSource.getMoviesByFilter(filter.countryId, filter.genreId) ?: emptyList()
        return getAppCollection(name, index, filter, movies)
    }

    private suspend fun getTop250(name: String, index: Int): AppCollection<Movie> {
        return getAppCollection(name, index, filter = null, collectionsSource.getTop250())
    }

    private suspend fun getTvSeries(name: String, index: Int): AppCollection<Movie> {
        return getAppCollection(name, index, filter = null, collectionsSource.getSeries())
    }

    private fun getAppCollection(
        name: String,
        index: Int,
        filter: MoviesFilter?,
        movies: List<Movie>
    ): AppCollection<Movie> {
        val limitedMovies =
            MoviesUtils.limitMovies(movies, Constants.MAX_MOVIES_COLLECTION_SIZE)
        return AppCollection(index, name, filter, limitedMovies)
    }
}