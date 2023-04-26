package ru.skillbox.feature_film_page.usecases

import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.feature_film_page.repositories.FilmPageRepository
import javax.inject.Inject

class SetFavouriteUseCase @Inject constructor(
    private val filmPageRepository: FilmPageRepository
) {

    suspend fun execute(movie: MovieDetails): Boolean {
        return filmPageRepository.setFavourite(movie)
    }
}