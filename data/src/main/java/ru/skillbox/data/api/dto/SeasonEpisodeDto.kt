package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.SeasonEpisode

@JsonClass(generateAdapter = true)
class SeasonEpisodeDto(
    @Json(ignore = true) override val filmId: Int = 0,
    @Json(name = "seasonNumber") override val seasonNumber: Int,
    @Json(name = "episodeNumber") override val episodeNumber: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "releaseDate") override val releaseDate: String?,
    @Json(name = "synopsis") val synopsis: String?
) : SeasonEpisode