package ru.skillbox.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbox.data.api.dto.*
import ru.skillbox.data.secret.Secret

interface KinopoiskApi {

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/filters")
    suspend fun getCountriesAndGenres(): Response<CountryAndGenresDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremiers(
        @Query(value = "year") year: Int,
        @Query(value = "month") month: String
    ): Response<PremiersDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopular(@Query(value = "page") pageNumber: Int): Response<PopularDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films")
    suspend fun getByFilters(
        @Query(value = "countries") country: Int,
        @Query(value = "genres") genre: Int,
        @Query(value = "page") pageNumber: Int
    ): Response<RandomCollectionDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/top")
    suspend fun getTop250(@Query(value = "page") pageNumber: Int): Response<Top250Dto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films?type=TV_SERIES")
    suspend fun getTvSeries(@Query(value = "page") pageNumber: Int): Response<TVSeriesDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/{id}")
    suspend fun getMovieDetails(@Path("id") id: Long): Response<MovieDetailsDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v1/staff")
    suspend fun getMovieStaff(@Query("filmId") id: Long): Response<List<StaffItemDto>>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSeasons(@Path("id") id: Int): Response<SeasonResponseDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun getGallery(@Path("id") id: Int, @Query("page") page: Int): Response<MovieImagesDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilars(@Path("id") id: Int): Response<RelatedMoviesDto>
}