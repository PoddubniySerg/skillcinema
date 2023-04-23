package ru.skillbox.feature_film_page.usecases

import ru.skillbox.feature_film_page.models.MovieImagesGallery
import ru.skillbox.feature_film_page.repositories.FilmPageRepository
import javax.inject.Inject

class GetMovieGalleryUseCase @Inject constructor(
    private val filmPageRepository: FilmPageRepository
) {

    suspend fun execute(filmId: Long): MovieImagesGallery {
        val gallery = filmPageRepository.getGallery(filmId)
        return MovieImagesGallery(filmId, gallery)
    }
}