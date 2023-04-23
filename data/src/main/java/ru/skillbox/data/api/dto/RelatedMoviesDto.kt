package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.RelatedMovies

@JsonClass(generateAdapter = true)
class RelatedMoviesDto(
    @Json(name = "total") override val total: Int,
    @Json(name = "items") override val movies: List<RelatedMovieDto>
) : RelatedMovies