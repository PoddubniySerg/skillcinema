package ru.skillbox.data.device.dao.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class MovieWithCollections(
    @Embedded val movie: MovieDao,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "collection_name",
        associateBy = Junction(CollectionAndMoviesCrossRef::class)
    )
    val collections: List<CollectionDao>
)