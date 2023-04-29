package ru.skillbox.data.repositories

import ru.skillbox.data.repositories.interfaces.CinemaApi
import ru.skillbox.data.repositories.interfaces.DeviceDao
import ru.skillbox.feature_home.domain.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    cinemaApi: CinemaApi,
    deviceDao: DeviceDao
) : LoaderRepositoryImpl(cinemaApi, deviceDao), HomeRepository {

    override suspend fun filterMoviesByCollection(
        movies: List<Long>,
        collection: String
    ): List<Long> {
        return deviceDao.filterMoviesByCollection(movies, collection)
    }

}