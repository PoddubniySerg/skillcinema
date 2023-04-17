package ru.skillbox.feature_film_page.models

import ru.skillbox.core.domain.entities.SeasonEpisode
import ru.skillbox.core.domain.entities.TranslatableName

class TranslatableEpisode(
    override val filmId: Int,
    override val seasonNumber: Int,
    override val episodeNumber: Int,
    override val nameRu: String?,
    override val nameEn: String?,
    override val releaseDate: String?
) : SeasonEpisode, TranslatableName