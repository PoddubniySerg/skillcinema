package ru.skillbox.feature_film_page.usecases

import ru.skillbox.core.domain.entities.StaffItem
import ru.skillbox.feature_film_page.repositories.FilmPageRepository
import javax.inject.Inject

open class GetMovieStaffUseCase @Inject constructor(
    private val filmPageRepository: FilmPageRepository
) {

    suspend fun execute(id: Long): List<StaffItem> {
        return filmPageRepository.getFilmStaff(id)
    }
}