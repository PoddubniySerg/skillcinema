package ru.skillbox.core.domain.entities

interface Movie {
    val id: Int
    val nameRu: String?
    val nameEn: String?
    val posterUrl: String?
    val posterUrlPreview: String?
    var seen: Boolean
}