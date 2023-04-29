package ru.skillbox.data.device.dao

import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.utils.RequiredCollections
import ru.skillbox.data.DataApp
import ru.skillbox.data.device.dao.dto.CollectionAndMoviesCrossRef
import ru.skillbox.data.device.dao.dto.CollectionDao
import ru.skillbox.data.device.dao.dto.GenreDao
import ru.skillbox.data.device.dao.dto.MovieDao
import ru.skillbox.data.device.dao.dto.MoviesGenresCrossRef
import ru.skillbox.data.repositories.interfaces.DeviceDao
import javax.inject.Inject

class DeviceDaoImpl @Inject constructor() : DeviceDao {

    private val dbClient = DataApp.getDataBase().dbClient()

    override suspend fun newCollection(name: String) {
        val collection = CollectionDao(name)
        dbClient.saveCollection(collection)
    }

    override suspend fun setFavourite(movie: MovieDetails): Boolean {
        val collection = RequiredCollections.FAVOURITE_COLLECTION.name
        return setCollection(movie, collection)
    }

    override suspend fun setWillView(movie: MovieDetails): Boolean {
        val collection = RequiredCollections.WILL_VIEW_COLLECTION.name
        return setCollection(movie, collection)
    }

    override suspend fun setViewed(movie: MovieDetails): Boolean {
        val collection = RequiredCollections.VIEWED_COLLECTION.name
        return setCollection(movie, collection)
    }

    override suspend fun getCollections(filmId: Long): List<String> {
        return dbClient.getMovieWithCollections(filmId)?.collections?.map { it.name } ?: emptyList()
    }

    override suspend fun isCollectionExist(name: String): Boolean {
        return dbClient.isCollectionExist(name)
    }

    override suspend fun filterMoviesByCollection(
        movies: List<Long>,
        collection: String
    ): List<Long> {
        return dbClient.filterMoviesByCollection(movies, collection)
    }

    private suspend fun setCollection(movie: MovieDetails, collection: String): Boolean {
        val isCollectionContainMovie = dbClient.isCollectionContainMovie(collection, movie.id)
        val relation = CollectionAndMoviesCrossRef(collection, movie.id)
        return if (isCollectionContainMovie) {
            dbClient.deleteCollectionMovieRelation(relation)
            false
        } else {
            saveMovieInCollection(movie, collection, relation)
            true
        }
    }

    private suspend fun saveMovieInCollection(
        movie: MovieDetails,
        collection: String,
        relation: CollectionAndMoviesCrossRef
    ) {
        val isMovieNotExist = !dbClient.isMovieExist(movie.id)
        if (isMovieNotExist) {
            saveMovie(movie)
        }
        val isCollectionNotExist = !dbClient.isCollectionExist(collection)
        if (isCollectionNotExist) {
            saveCollection(collection)
        }
        dbClient.setCollectionMovieRelation(relation)
    }

    private suspend fun saveMovie(movie: MovieDetails) {
        val rating =
            if (movie.ratingKinopoisk == null) {
                null
            } else {
                String.format("%.1f", movie.ratingKinopoisk)
            }
        val movieDao = MovieDao(
            movie.id,
            rating,
            movie.nameRu,
            movie.nameEn,
            movie.posterUrl,
            movie.posterUrlPreview
        )
        dbClient.saveMovie(movieDao)
        movie.genres?.forEach {
            val genre = it.genre
            if (genre != null) {
                dbClient.saveGenre(GenreDao(genre))
                val relationGenre = MoviesGenresCrossRef(movie.id, genre)
                dbClient.setMovieGenreRelation(relationGenre)
            }
        }
    }

    private suspend fun saveCollection(collection: String) {
        val collectionDao = CollectionDao(collection)
        dbClient.saveCollection(collectionDao)
    }
}