package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.MovieDetails

@JsonClass(generateAdapter = true)
class MovieDetailsDto(
    @Json(name = "kinopoiskId") override val id: Long,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameOriginal") override val nameOriginal: String?,
    @Json(name = "posterUrl") override val posterUrl: String?,
    @Json(name = "logoUrl") override val logoUrl: String?,
    @Json(name = "ratingKinopoisk") override val ratingKinopoisk: Double?,
    @Json(name = "year") override val year: Int?,
    @Json(name = "filmLength") override val filmLength: Int?,
    @Json(name = "description") override val description: String?,
    @Json(name = "shortDescription") override val shortDescription: String?,
    @Json(name = "ratingAgeLimits") override val ratingAgeLimits: String?,
    @Json(name = "countries") override val countries: List<CountryObjectDto>?,
    @Json(name = "genres") override val genres: List<GenreObjectDto>?,
    @Json(name = "serial") override val isSerial: Boolean?,
    @Json(name = "imdbId") val imdbId: String?,
    @Json(name = "coverUrl") override val coverUrl: String?,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String?,
    @Json(name = "reviewsCount") val reviewsCount: Int?,
    @Json(name = "ratingGoodReview") val ratingGoodReview: Double?,
    @Json(name = "ratingGoodReviewVoteCount") val ratingGoodReviewVoteCount: Int?,
    @Json(name = "ratingKinopoiskVoteCount") val ratingKinopoiskVoteCount: Int?,
    @Json(name = "ratingImdb") val ratingImdb: Double?,
    @Json(name = "ratingImdbVoteCount") val ratingImdbVoteCount: Int?,
    @Json(name = "ratingFilmCritics") val ratingFilmCritics: Double?,
    @Json(name = "ratingFilmCriticsVoteCount") val ratingFilmCriticsVoteCount: Int?,
    @Json(name = "ratingAwait") val ratingAwait: Double?,
    @Json(name = "ratingAwaitCount") val ratingAwaitCount: Int?,
    @Json(name = "ratingRfCritics") val ratingRfCritics: Double?,
    @Json(name = "ratingRfCriticsVoteCount") val ratingRfCriticsVoteCount: Int?,
    @Json(name = "webUrl") val webUrl: String?,
    @Json(name = "slogan") val slogan: String?,
    @Json(name = "editorAnnotation") val editorAnnotation: String?,
    @Json(name = "isTicketsAvailable") val isTicketsAvailable: Boolean?,
    @Json(name = "productionStatus") val productionStatus: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "ratingMpaa") val ratingMpaa: String?,
    @Json(name = "hasImax") val hasImax: Boolean?,
    @Json(name = "has3D") val has3D: Boolean?,
    @Json(name = "lastSync") val lastSync: String?,
    @Json(name = "startYear") val startYear: Int?,
    @Json(name = "endYear") val endYear: Int?,
    @Json(name = "shortFilm") val shortFilm: Boolean?,
    @Json(name = "") val completed: Boolean?
) : MovieDetails