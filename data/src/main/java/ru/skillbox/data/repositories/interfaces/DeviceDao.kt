package ru.skillbox.data.repositories.interfaces

import ru.skillbox.core.domain.entities.MovieDetails

interface DeviceDao {

    suspend fun getCountCollectionByName(name: String): Int

    suspend fun newCollection(name: String)

    suspend fun setFavourite(movie: MovieDetails): Boolean

    suspend fun getCollections(filmId: Long): List<String>
}