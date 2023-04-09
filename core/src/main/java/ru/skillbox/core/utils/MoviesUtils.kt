package ru.skillbox.core.utils

import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.entities.PremierMovie
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.streams.toList

object MoviesUtils {

    fun filterPremiersByDate(
        movies: List<PremierMovie>,
        dateFrom: LocalDate,
        dateTo: LocalDate,
        formatter: DateTimeFormatter
    ): List<PremierMovie> {
        return movies.stream()
            .filter { movie ->
                val date =
                    movie.premiereRu?.let { dateString ->
                        LocalDate.parse(dateString, formatter)
                    }
                (date != null) && (date > dateFrom) && (date < dateTo)
            }
            .limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
            .toList()
    }

    fun limitMovies(movies: List<Movie>, maxSize: Long): List<Movie> {
        return movies.stream()
            .limit(maxSize)
            .toList()
    }
}