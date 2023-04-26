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

    private val daoRepository = DataApp.getDataBase().accountPageDao()

    override suspend fun getCountCollectionByName(name: String): Int {
        return daoRepository.getCountCollectionByName(name)
    }

    override suspend fun newCollection(name: String) {
        val collection = CollectionDao(name)
        daoRepository.saveCollection(collection)
    }

    override suspend fun setFavourite(movie: MovieDetails): Boolean {
        val collection = RequiredCollections.FAVOURITE_COLLECTION.name
        val isMovieFavourite = daoRepository.isCollectionContainMovie(collection, movie.id)
        val relation = CollectionAndMoviesCrossRef(collection, movie.id)
        return if (isMovieFavourite) {
            daoRepository.deleteCollectionMovieRelation(relation)
            false
        } else {
            saveMovieInCollection(movie, collection, relation)
            true
        }
    }

    override suspend fun getCollections(filmId: Long): List<String> {
        return daoRepository.getMovieWithCollections(filmId)?.collections?.map { it.name } ?: emptyList()
    }

    private suspend fun saveMovieInCollection(
        movie: MovieDetails,
        collection: String,
        relation: CollectionAndMoviesCrossRef
    ) {
        val isMovieNotExist = !daoRepository.isMovieExist(movie.id)
        if (isMovieNotExist) {
            saveMovie(movie)
        }
        val isCollectionNotExist = !daoRepository.isCollectionExist(collection)
        if (isCollectionNotExist) {
            saveCollection(collection)
        }
        daoRepository.setCollectionMovieRelation(relation)
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
        daoRepository.saveMovie(movieDao)
        movie.genres?.forEach {
            val genre = it.genre
            if (genre != null) {
                daoRepository.saveGenre(GenreDao(genre))
                val relationGenre = MoviesGenresCrossRef(movie.id, genre)
                daoRepository.setMovieGenreRelation(relationGenre)
            }
        }
    }

    private suspend fun saveCollection(collection: String) {
        val collectionDao = CollectionDao(collection)
        daoRepository.saveCollection(collectionDao)
    }
}