package ru.skillbox.core.domain.entities

interface SeasonEpisode {
    val filmId: Int
    val seasonNumber: Int
    val episodeNumber: Int
    val nameRu: String?
    val nameEn: String?
    val releaseDate: String?
}