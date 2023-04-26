package ru.skillbox.core.domain.entities

interface MovieDetails {

    val id: Long
    val nameRu: String?
    val nameEn: String?
    val nameOriginal: String?
    val posterUrl: String?
    val coverUrl: String?
    val posterUrlPreview: String?
    val logoUrl: String?
    val ratingKinopoisk: Double?
    val year: Int?
    val filmLength: Int?
    val description: String?
    val shortDescription: String?
    val ratingAgeLimits: String?
    val countries: List<CountryObject>?
    val genres: List<GenreObject>?
    val isSerial: Boolean?
}