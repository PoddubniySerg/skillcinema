package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.GenreObject

@JsonClass(generateAdapter = true)
class GenreObjectDto(
    @Json(name = "id") override val id: Int?,
    @Json(name = "genre") override val genre: String?
) : GenreObject