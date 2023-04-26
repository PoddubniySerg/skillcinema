package ru.skillbox.data.device.dao.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["movie_id", "genre"])
class MoviesGenresCrossRef (
    @ColumnInfo(name = "movie_id") val movieId: Long,
    @ColumnInfo(name = "genre") val genre: String
)