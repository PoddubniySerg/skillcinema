package ru.skillbox.core.domain.entities

interface MovieImages {
    val total: Int
    val totalPages: Int
    val images: List<MovieImage>?
}