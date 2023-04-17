package ru.skillbox.feature_film_page.models

import ru.skillbox.core.domain.entities.SeasonEpisode
import ru.skillbox.core.domain.entities.SerialSeason
import ru.skillbox.core.domain.entities.TranslatableName

class TranslatableSeason(
    override val filmId: Int,
    override val number: Int,
    override val episodes: List<TranslatableEpisode>
) : SerialSeason