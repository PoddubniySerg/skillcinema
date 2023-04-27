package ru.skillbox.data.device.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.skillbox.data.device.dao.dto.CollectionAndMoviesCrossRef
import ru.skillbox.data.device.dao.dto.CollectionDao
import ru.skillbox.data.device.dao.dto.GenreDao
import ru.skillbox.data.device.dao.dto.MovieDao
import ru.skillbox.data.device.dao.dto.MoviesGenresCrossRef

@Database(
    entities = [
        CollectionDao::class,
        GenreDao::class,
        MovieDao::class,
        MoviesGenresCrossRef::class,
        CollectionAndMoviesCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dbClient(): DbClient
}