package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.GenreString

@JsonClass(generateAdapter = true)
class GenreDto(@Json(name = "genre") override val genre: String) : GenreString