package ru.skillbox.data.device.dao.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class AccountMovieDto(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    val id: Int,

    @ColumnInfo(name = "poster_url_preview")
    val posterUrlPreview: String?,

    @ColumnInfo(index = true, name = "name_ru")
    val nameRu: String?,

    @ColumnInfo(index = true, name = "name_en")
    val nameEn: String?,

    @ColumnInfo("genres")
    val genres: String?,

    @ColumnInfo(name = "rating")
    val rating: String?,

    @ColumnInfo(name = "is_seen")
    var seen: Boolean,

    @ColumnInfo(name = "poster_url")
    val posterUrl: String?

)