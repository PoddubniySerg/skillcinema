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
interface DbClient {

    /*
    *Сохранить новую коллекцию в БД
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCollection(collection: CollectionDao)

    /*
    *Сохранить новый жанр в БД
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveGenre(genre: GenreDao)

    /*
    *Сохранить новый фильм или сериал в БД
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(movie: MovieDao)

    /*
    *Записать в БД новую связь таблицы фильмов/сериалов с таблицей жанров
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setMovieGenreRelation(relation: MoviesGenresCrossRef)

    /*
    *Записать в БД новую связь таблицы фильмов/сериалов с таблицей коллекций
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setCollectionMovieRelation(relation: CollectionAndMoviesCrossRef)

    /*
    *Удалить из БД связь таблицы фильмов/сериалов с таблицей коллекций
    * */
    @Delete
    suspend fun deleteCollectionMovieRelation(relation: CollectionAndMoviesCrossRef)

    /*
    *Получить фильм/сериал и связанный с ним список коллекций
    * */
    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id = :filmId")
    suspend fun getMovieWithCollections(filmId: Long): MovieWithCollections?

    /*
    *Проверить, существует ли в БД фильм или сериал с указанным id
    * */
    @Query("SELECT COUNT() > 0 FROM movies WHERE movie_id = :filmId")
    suspend fun isMovieExist(filmId: Long): Boolean

    /*
    *Проверить, содержит ли коллекция фильм или сериал с указанным id
    * */
    @Query("SELECT COUNT() > 0 FROM collectionandmoviescrossref WHERE movie_id = :filmId AND collection_name = :collection")
    suspend fun isCollectionContainMovie(collection: String, filmId: Long): Boolean

    /*
    *Проверить, существует ли в БД коллекция с указанным названием
    * */
    @Query("SELECT COUNT() > 0 FROM collections WHERE collection_name = :name")
    suspend fun isCollectionExist(name: String): Boolean

    /*
    * Выбрать все id фильмов из списка, связанных с указанной коллекцией
    * */
    @Query("SELECT movie_id FROM collectionandmoviescrossref WHERE movie_id IN (:movies) AND collection_name = :collection")
    suspend fun filterMoviesByCollection(movies: List<Long>, collection: String): List<Long>

    /*
    *Получить фильм/сериал и связанный с ним список жанров
    * */
    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id = :filmId")
    suspend fun getMovieById(filmId: Long): MovieWithGenres?

    /*
    *Получить коллекцию и связанный с ним список фильмов/сериалов
    * */
    @Transaction
    @Query("SELECT * FROM collections WHERE collection_name = :collection")
    suspend fun getCollectionWithMovies(collection: String): CollectionWithMovies?
}