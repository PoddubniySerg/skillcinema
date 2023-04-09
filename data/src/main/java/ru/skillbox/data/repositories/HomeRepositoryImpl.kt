package ru.skillbox.data.repositories

import ru.skillbox.data.repositories.interfaces.CinemaApi
import ru.skillbox.feature_home.domain.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    cinemaApi: CinemaApi
) : LoaderRepositoryImpl(cinemaApi), HomeRepository