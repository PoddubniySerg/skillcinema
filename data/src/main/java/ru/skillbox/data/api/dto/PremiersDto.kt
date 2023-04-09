package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PremiersDto(
    @Json(name = "total") val total: Int?,
    @Json(name = "items") val items: List<PremierMovieDto>?
)