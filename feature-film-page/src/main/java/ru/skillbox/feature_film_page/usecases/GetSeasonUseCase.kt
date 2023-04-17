package ru.skillbox.feature_film_page.usecases

import ru.skillbox.core.domain.entities.SerialSeason
import ru.skillbox.feature_film_page.repositories.FilmPageRepository
import javax.inject.Inject

class GetSeasonUseCase @Inject constructor(
    private val filmPageRepository: FilmPageRepository
) {

    suspend fun execute(filmId: Long): List<SerialSeason> {
        return filmPageRepository.getSeriesSeasons(filmId)
    }
}