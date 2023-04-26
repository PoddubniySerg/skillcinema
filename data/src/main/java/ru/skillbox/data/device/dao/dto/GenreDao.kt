package ru.skillbox.data.device.dao.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
class GenreDao(
    @PrimaryKey
    @ColumnInfo(name = "genre")
    val genre: String
)