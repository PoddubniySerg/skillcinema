package ru.skillbox.feature_film_page.models

import ru.skillbox.core.domain.entities.StaffItem
import ru.skillbox.core.domain.entities.StaffProfessionKey
import ru.skillbox.core.domain.entities.TranslatableName

class TranslatableStaffItem(
    override val staffId: Long,
    override val nameRu: String?,
    override val nameEn: String?,
    override val description: String?,
    override val posterUrl: String?,
    override val profession: String,
    override val professionKey: StaffProfessionKey
) : StaffItem, TranslatableName