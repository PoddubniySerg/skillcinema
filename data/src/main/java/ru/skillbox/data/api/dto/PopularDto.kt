package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PopularDto(
    @Json(name = "pagesCount") val pagesCount: Int,
    @Json(name = "films") val films: List<PopularMovieDto>
)