package ru.skillbox.data.repositories.interfaces

import ru.skillbox.core.domain.entities.MovieDetails

interface DeviceDao {

    suspend fun newCollection(name: String)

    suspend fun setFavourite(movie: MovieDetails): Boolean

    suspend fun setWillView(movie: MovieDetails): Boolean

    suspend fun setViewed(movie: MovieDetails): Boolean

    suspend fun getCollections(filmId: Long): List<String>

    suspend fun isCollectionExist(name: String): Boolean
}