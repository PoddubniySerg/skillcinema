package ru.skillbox.core.domain.entities

interface MainMovie : Movie {

    val genres: List<GenreString>?
    val rating: String?
}