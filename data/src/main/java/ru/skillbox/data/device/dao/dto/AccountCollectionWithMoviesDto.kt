package ru.skillbox.data.device.dao.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class AccountCollectionWithMoviesDto(
    @Embedded val collection: AccountCollectionDto,
    @Relation(
        parentColumn = "collection_id",
        entityColumn = "movie_id",
        associateBy = Junction(AccountCollectionMovieCrossRef::class)
    )
    val movies: List<AccountMovieDto>
)