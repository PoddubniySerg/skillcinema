package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.SerialSeason

@JsonClass(generateAdapter = true)
class SerialSeasonDto(
    @Json(ignore = true) override val filmId: Int = 0,
    @Json(name = "number") override val number: Int,
    @Json(name = "episodes") override val episodes: List<SeasonEpisodeDto>
) : SerialSeason