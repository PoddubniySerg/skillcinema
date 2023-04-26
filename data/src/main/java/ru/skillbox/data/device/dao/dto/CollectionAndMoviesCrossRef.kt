package ru.skillbox.data.device.dao.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["collection_name", "movie_id"])
class CollectionAndMoviesCrossRef (
    @ColumnInfo(name = "collection_name") val collectionName: String,
    @ColumnInfo(name = "movie_id") val movieId: Long
)