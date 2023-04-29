package ru.skillbox.feature_home.domain

import ru.skillbox.core.domain.repositories.MoviesCollectionsSource

interface HomeRepository : MoviesCollectionsSource {

    suspend fun filterMoviesByCollection(movies: List<Long>, collection: String): List<Long>
}