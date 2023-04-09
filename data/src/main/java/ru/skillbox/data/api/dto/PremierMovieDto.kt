package ru.skillbox.data.api.dto

import ru.skillbox.data.api.dto.CountryDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.data.api.dto.GenreDto

@JsonClass(generateAdapter = true)
class PremierMovieDto(
    @Json(name = "kinopoiskId") override val id: Int,
    @Json(name = "posterUrl") override val posterUrl: String?,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "genres") override val genres: List<GenreDto>?,
    @Json(name = "premiereRu") override val premiereRu: String?,
    @Json(ignore = true) override var seen: Boolean = false,
    @Json(name = "year") val year: Int?,
    @Json(name = "countries") val countries: List<CountryDto>?,
    @Json(name = "duration") val duration: Int?
) : PremierMovie