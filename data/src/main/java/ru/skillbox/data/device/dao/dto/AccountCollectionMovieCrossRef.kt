package ru.skillbox.data.device.dao.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "collection_movie_cross_ref", primaryKeys = ["collection_id", "movie_id"])
class AccountCollectionMovieCrossRef(
    @ColumnInfo(name = "collection_id") val collectionId: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int
)