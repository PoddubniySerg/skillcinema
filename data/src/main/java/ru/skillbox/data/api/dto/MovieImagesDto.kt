package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.MovieImages

@JsonClass(generateAdapter = true)
class MovieImagesDto(
    @Json(name = "total") override val total: Int,
    @Json(name = "totalPages") override val totalPages: Int,
    @Json(name = "items") override val images: List<MovieImageDto>?
) : MovieImages