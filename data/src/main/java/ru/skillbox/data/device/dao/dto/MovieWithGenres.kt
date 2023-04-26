package ru.skillbox.data.device.dao.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class MovieWithGenres(
    @Embedded val movie: MovieDao,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "genre",
        associateBy = Junction(MoviesGenresCrossRef::class)
    )
    val genres: List<GenreDao>
)