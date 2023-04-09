package ru.skillbox.core.domain.entities

interface CountriesAndGenres {

    val genres: List<GenreObject>?
    val countries: List<CountryObject>?
}