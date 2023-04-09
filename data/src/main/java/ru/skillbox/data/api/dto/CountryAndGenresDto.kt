package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.CountriesAndGenres

@JsonClass(generateAdapter = true)
class CountryAndGenresDto(
    @Json(name = "genres") override val genres: List<GenreObjectDto>,
    @Json(name = "countries") override val countries: List<CountryObjectDto>
) : CountriesAndGenres