package ru.skillbox.feature_film_page.utils

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatTextView
import ru.skillbox.core.domain.entities.CountryObject
import ru.skillbox.core.domain.entities.GenreObject
import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.TranslatableName
import ru.skillbox.core.utils.TranslatableNameProcessor
import ru.skillbox.feature_film_page.models.CountryObjectDto
import ru.skillbox.feature_film_page.models.GenreObjectDto
import ru.skillbox.feature_film_page.models.MovieDetailsDto

object MovieDetailsProcessor {

    fun convertToMovieDetailsDto(movieDetails: MovieDetails): MovieDetailsDto {
        val countries = movieDetails.countries?.map { convertToCountryObjectDto(it) }
        val genres = movieDetails.genres?.map { convertToGenreObjectDto(it) }
        return MovieDetailsDto(
            movieDetails.id,
            movieDetails.nameRu,
            movieDetails.nameEn,
            movieDetails.nameOriginal,
            movieDetails.posterUrl,
            movieDetails.logoUrl,
            movieDetails.ratingKinopoisk,
            movieDetails.year,
            movieDetails.filmLength,
            movieDetails.description,
            movieDetails.shortDescription,
            movieDetails.ratingAgeLimits,
            countries,
            genres,
            movieDetails.isSerial,
            movieDetails.coverUrl
        )
    }

    @SuppressLint("SetTextI18n")
    fun setRatingNameValue(movieDetails: MovieDetails, filmRatingName: AppCompatTextView) {
        val name = TranslatableNameProcessor.getName(movieDetails as TranslatableName)
        val rating = if (movieDetails.ratingKinopoisk != null) {
            String.format("%.1f", movieDetails.ratingKinopoisk)
        } else {
            ""
        }
        val original =
            if (movieDetails.nameOriginal != null) {
                " ${movieDetails.nameOriginal}"
            } else {
                ""
            }
        filmRatingName.text = String.format("%s %s", rating, name) + original
    }

    fun setYearGenresValue(movieDetails: MovieDetails, filmYearGenres: AppCompatTextView) {
        val year = if (movieDetails.year != null) {
            String.format("%d", movieDetails.year)
        } else {
            ""
        }
        filmYearGenres.text =
            String.format(
                "%s, %s",
                year,
                movieDetails.genres?.joinToString(", ") ?: ""
            )
    }

    fun setCountriesLengthAgeLimit(
        movieDetails: MovieDetails,
        filmCountriesLengthAgeLimit: AppCompatTextView,
        pattern: String
    ) {
        val countries = movieDetails.countries?.joinToString(", ") ?: ""
        val hours = movieDetails.filmLength?.div(60) ?: 0
        val minutes = movieDetails.filmLength?.rem(60) ?: 0
        val ageLimit = movieDetails.ratingAgeLimits?.substringAfter("age")
        filmCountriesLengthAgeLimit.text =
            String.format(
                pattern,
                countries,
                hours,
                minutes,
                ageLimit
            )
    }

    private fun convertToCountryObjectDto(countryObject: CountryObject): CountryObjectDto {
        return CountryObjectDto(countryObject.id, countryObject.country)
    }

    private fun convertToGenreObjectDto(genreObject: GenreObject): GenreObjectDto {
        return GenreObjectDto(genreObject.id, genreObject.genre)
    }
}