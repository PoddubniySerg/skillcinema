package ru.skillbox.data.device.dao

import androidx.room.*
import ru.skillbox.data.device.dao.dto.CollectionAndMoviesCrossRef
import ru.skillbox.data.device.dao.dto.CollectionDao
import ru.skillbox.data.device.dao.dto.CollectionWithMovies
import ru.skillbox.data.device.dao.dto.GenreDao
import ru.skillbox.data.device.dao.dto.MovieDao
import ru.skillbox.data.device.dao.dto.MovieWithCollections
import ru.skillbox.data.device.dao.dto.MovieWithGenres
import ru.skillbox.data.device.dao.dto.MoviesGenresCrossRef

@Dao
interface DaoRepository {

    @Query("SELECT COUNT() FROM collections WHERE collection_name = :name")
    suspend fun getCountCollectionByName(name: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCollection(collection: CollectionDao)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveGenre(genre: GenreDao)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(movie: MovieDao)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setMovieGenreRelation(relation: MoviesGenresCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setCollectionMovieRelation(relation: CollectionAndMoviesCrossRef)

    @Delete
    suspend fun deleteCollectionMovieRelation(relation: CollectionAndMoviesCrossRef)

    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id = :filmId")
    suspend fun getMovieById(filmId: Long): MovieWithGenres?

    @Transaction
    @Query("SELECT * FROM collections WHERE collection_name = :collection")
    suspend fun getCollectionWithMovies(collection: String): CollectionWithMovies?

    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id = :filmId")
    suspend fun getMovieWithCollections(filmId: Long): MovieWithCollections?

    @Query("SELECT COUNT() > 0 FROM movies WHERE movie_id = :filmId")
    suspend fun isMovieExist(filmId: Long): Boolean

    @Query("SELECT COUNT() > 0 FROM collectionandmoviescrossref WHERE movie_id = :filmId AND collection_name = :collection")
    suspend fun isCollectionContainMovie(collection: String, filmId: Long): Boolean

    @Query("SELECT COUNT() > 0 FROM collections WHERE collection_name = :name")
    suspend fun isCollectionExist(name: String): Boolean

//    @Query("SELECT * FROM collections")
//    fun getCollections(): List<AccountCollectionDto>

//    @Query("SELECT COUNT() FROM movies WHERE name_ru = :nameRu AND name_en = :nameEn")
//    fun getCountMovieByName(nameRu: String, nameEn: String): Int

//    @Transaction
//    @Query("SELECT * FROM collections WHERE collection_id = :id")
//    fun getAccountCollectionWithMovies(id: Int): AccountCollectionWithMoviesDto

//    @Transaction
//    @Query("SELECT * FROM collections WHERE collection_id = :id LIMIT :limit")
//    fun getAccountCollectionWithMoviesLimit(id: Int, limit: Int): AccountCollectionWithMoviesDto

//    @Query("SELECT COUNT() FROM collection_movie_cross_ref WHERE movie_id = :movieId")
//    fun getCountCollectionsForMovie(movieId: Int): Int

//    @Insert(onConflict = OnConflictStrategy.ABORT)
//    fun saveCollection(collectionDto: AccountCollectionDto)

//    @Insert(onConflict = OnConflictStrategy.ABORT)
//    fun saveMovie(movieDto: AccountMovieDto)

//    @Update
//    fun updateMovie(movieDto: AccountMovieDto)

//    @Insert(onConflict = OnConflictStrategy.ABORT)
//    fun saveCrossRef(crossRef: AccountCollectionMovieCrossRef)

//    @Query("DELETE FROM collections WHERE collection_id = :id")
//    fun removeCollection(id: Int)

//    @Query("DELETE FROM movies WHERE movie_id = :id")
//    fun removeMovie(id: Int)

//    @Query("DELETE FROM collection_movie_cross_ref WHERE collection_id = :collectionId AND movie_id = :movieId")
//    fun remove(collectionId: Int, movieId: Int)
}