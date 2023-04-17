package ru.skillbox.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.skillbox.core.domain.entities.StaffItem
import ru.skillbox.core.domain.entities.StaffProfessionKey

@JsonClass(generateAdapter = true)
class StaffItemDto(
    @Json(name = "staffId") override val staffId: Long,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "description") override val description: String?,
    @Json(name = "posterUrl") override val posterUrl: String?,
    @Json(name = "professionText") override val profession: String,
    @Json(name = "professionKey") override val professionKey: StaffProfessionKey
) : StaffItem