package ru.skillbox.core.domain.entities

interface RelatedMovies {

    val total: Int
    val movies: List<RelatedMovie>
}