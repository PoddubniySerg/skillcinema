package ru.skillbox.feature_film_page.models

import ru.skillbox.core.domain.entities.RelatedMovie
import ru.skillbox.core.domain.entities.TranslatableName

class TranslatableRelatedMovie(
    override val id: Int,
    override val nameRu: String?,
    override val nameEn: String?,
    override val posterUrl: String?,
    override val posterUrlPreview: String?,
    override var seen: Boolean,
    override val nameOriginal: String?
) : RelatedMovie, TranslatableName