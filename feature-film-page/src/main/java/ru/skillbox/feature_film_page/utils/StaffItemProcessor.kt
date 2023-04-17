package ru.skillbox.feature_film_page.utils

import ru.skillbox.core.domain.entities.StaffItem
import ru.skillbox.feature_film_page.models.TranslatableStaffItem

object StaffItemProcessor {

    fun convertToTranslatable(staffItem: StaffItem): TranslatableStaffItem {
        return TranslatableStaffItem(
            staffItem.staffId,
            staffItem.nameRu,
            staffItem.nameEn,
            staffItem.description,
            staffItem.posterUrl,
            staffItem.profession,
            staffItem.professionKey
        )
    }
}