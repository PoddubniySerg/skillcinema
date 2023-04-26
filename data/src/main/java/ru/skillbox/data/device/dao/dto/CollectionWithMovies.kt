package ru.skillbox.data.device.dao.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class CollectionWithMovies(
    @Embedded val collection: CollectionDao,
    @Relation(
        entity = MovieDao::class,
        parentColumn = "collection_name",
        entityColumn = "movie_id",
        associateBy = Junction(CollectionAndMoviesCrossRef::class)
    )
    val movies: List<MovieWithGenres>
)