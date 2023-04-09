package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.CountryObject

@JsonClass(generateAdapter = true)
class CountryObjectDto(
    @Json(name = "id") override val id: Int?,
    @Json(name = "country") override val country: String?
) : CountryObject