package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.MovieImage

@JsonClass(generateAdapter = true)
class MovieImageDto(
    @Json(name = "imageUrl") override val imageUrl: String?,
    @Json(name = "previewUrl") override val previewUrl: String?
) : MovieImage