package ru.skillbox.data.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.skillbox.data.DataApp
import ru.skillbox.data.R
import javax.inject.Inject

class CinemaApiSource @Inject constructor() {

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(DataApp.getContext().resources.getString(R.string.kinopoisk_base_url))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    fun getApi(): KinopoiskApi {
        return retrofit.create(KinopoiskApi::class.java)
    }
}