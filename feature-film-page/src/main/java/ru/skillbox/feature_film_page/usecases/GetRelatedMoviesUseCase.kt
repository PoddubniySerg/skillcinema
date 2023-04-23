package ru.skillbox.feature_film_page.usecases

import ru.skillbox.core.domain.entities.RelatedMovies
import ru.skillbox.feature_film_page.repositories.FilmPageRepository
import javax.inject.Inject

class GetRelatedMoviesUseCase @Inject constructor(
    private val filmPageRepository: FilmPageRepository
) {

    suspend fun execute(filmId: Long): RelatedMovies {
        return filmPageRepository.getSimilars(filmId)
    }
}