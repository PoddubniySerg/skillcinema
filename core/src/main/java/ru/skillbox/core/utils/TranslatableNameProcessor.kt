package ru.skillbox.core.utils

import androidx.appcompat.widget.AppCompatTextView
import ru.skillbox.core.domain.entities.TranslatableName
import java.util.*

object TranslatableNameProcessor {

    fun setName(translatable: TranslatableName, nameView: AppCompatTextView) {
        nameView.text = getName(translatable)
    }

    fun getName(translatable: TranslatableName): String {
        return if (Locale.getDefault().language == "ru") {
            val name = translatable.nameRu
            if (name == null || name.isEmpty() || name.isBlank()) {
                translatable.nameEn ?: ""
            } else {
                name
            }
        } else {
            val name = translatable.nameEn
            if (name == null || name.isEmpty() || name.isBlank()) {
                translatable.nameRu ?: ""
            } else {
                name
            }
        }
    }
}