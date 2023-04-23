package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.RelatedMovie

@JsonClass(generateAdapter = true)
class RelatedMovieDto(
    @Json(name = "filmId") override val id: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "posterUrl") override val posterUrl: String?,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String?,
    @Json(ignore = true) override var seen: Boolean = false,
    @Json(name = "nameOriginal") override val nameOriginal: String?,
    @Json(name = "relationType") relationType: String = "SIMILAR"
) : RelatedMovie