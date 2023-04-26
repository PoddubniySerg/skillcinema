package ru.skillbox.feature_film_page.usecases

import ru.skillbox.feature_film_page.repositories.FilmPageRepository
import javax.inject.Inject

class GetMovieCollectionsUseCase @Inject constructor(
    private val filmPageRepository: FilmPageRepository
) {

    suspend fun execute(filmId: Long): List<String> {
        return filmPageRepository.getCollections(filmId)
    }
}