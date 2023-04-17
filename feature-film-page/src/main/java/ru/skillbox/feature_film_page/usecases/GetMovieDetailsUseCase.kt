package ru.skillbox.feature_film_page.usecases

import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.feature_film_page.repositories.FilmPageRepository
import javax.inject.Inject

open class GetMovieDetailsUseCase @Inject constructor(
    private val filmPageRepository: FilmPageRepository
) {

    suspend fun execute(id: Long): MovieDetails {
        return filmPageRepository.getFilmById(id)
    }
}