package ru.skillbox.feature_home.domain

import ru.skillbox.core.domain.entities.Movie
import javax.inject.Inject

class SetMoviesByCollectionUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend fun execute(movies: List<Movie>, collection: String): List<Movie> {
        val ids = movies.map { it.id.toLong() }
        val filteredIds = homeRepository.filterMoviesByCollection(ids, collection)
        movies.forEach { it.seen = filteredIds.contains(it.id.toLong()) }
        return movies
    }
}