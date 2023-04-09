package ru.skillbox.core.utils

import ru.skillbox.core.domain.entities.CountriesAndGenres
import kotlin.random.Random

class CountryAndGenreSource(countriesAndGenres: CountriesAndGenres) {

    private val _countryId: Int
    private val _countryName: String
    private val _genreId: Int
    private val _genreName: String

    init {
        val randomIdCountry = Random.nextInt(0, countriesAndGenres.countries?.size ?: 0)
        val randomIdGenre = Random.nextInt(0, countriesAndGenres.genres?.size ?: 0)
        val country = countriesAndGenres.countries?.get(randomIdCountry)
        val genre = countriesAndGenres.genres?.get(randomIdGenre)
        this._countryId = country?.id ?: 1
        this._countryName = country?.country ?: "Unknown country"
        this._genreId = genre?.id ?: 1
        this._genreName = genre?.genre ?: "\"Unknown genre\""
    }

    val countryId get() = _countryId
    val countryName get() = _countryName
    val genreId get() = _genreId
    val genreName get() = _genreName
}