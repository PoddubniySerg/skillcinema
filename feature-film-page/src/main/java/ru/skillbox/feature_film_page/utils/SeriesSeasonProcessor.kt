package ru.skillbox.feature_film_page.utils

import ru.skillbox.core.domain.entities.SeasonEpisode
import ru.skillbox.core.domain.entities.SerialSeason
import ru.skillbox.feature_film_page.models.TranslatableEpisode
import ru.skillbox.feature_film_page.models.TranslatableSeason

object SeriesSeasonProcessor {

    fun convertToTranslatableSeason(season: SerialSeason): TranslatableSeason {
        return TranslatableSeason(
            season.filmId,
            season.number,
            season.episodes.map { convertToTranslatableEpisode(it) }
        )
    }

    private fun convertToTranslatableEpisode(episode: SeasonEpisode): TranslatableEpisode {
        return TranslatableEpisode(
            episode.filmId,
            episode.seasonNumber,
            episode.episodeNumber,
            episode.nameRu,
            episode.nameEn,
            episode.releaseDate
        )
    }
}