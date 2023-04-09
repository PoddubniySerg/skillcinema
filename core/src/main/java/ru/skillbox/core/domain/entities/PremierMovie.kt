package ru.skillbox.core.domain.entities

interface PremierMovie : Movie {
    val genres: List<GenreString>?
    val premiereRu: String?
}