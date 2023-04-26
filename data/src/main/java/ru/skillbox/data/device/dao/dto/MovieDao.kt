package ru.skillbox.data.device.dao.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class MovieDao(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "rating")
    val rating: String?,
    @ColumnInfo(name = "nameRu")
    val nameRu: String?,
    @ColumnInfo(name = "nameEn")
    val nameEn: String?,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String?,
    @ColumnInfo(name = "poster_url_preview")
    val posterUrlPreview: String?
)