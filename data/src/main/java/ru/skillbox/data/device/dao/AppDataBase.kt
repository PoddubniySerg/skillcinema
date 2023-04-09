package ru.skillbox.data.device.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.skillbox.data.device.dao.dto.AccountCollectionDto
import ru.skillbox.data.device.dao.dto.AccountCollectionMovieCrossRef
import ru.skillbox.data.device.dao.dto.AccountMovieDto

@Database(
    entities = [AccountCollectionDto::class, AccountMovieDto::class, AccountCollectionMovieCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountPageDao(): AccountPageDao
}