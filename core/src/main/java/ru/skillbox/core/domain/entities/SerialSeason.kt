package ru.skillbox.core.domain.entities

interface SerialSeason {
    val filmId: Int
    val number: Int
    val episodes: List<SeasonEpisode>
}