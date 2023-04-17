package ru.skillbox.feature_home.utils

import android.content.res.Resources
import android.os.Bundle
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.core.domain.models.MoviesCollection
import ru.skillbox.core.domain.models.MoviesFilter
import ru.skillbox.core.dto.MovieDto
import ru.skillbox.core.dto.PremierDto
import ru.skillbox.core.utils.Converter
import javax.inject.Inject

class CollectionsProcessor @Inject constructor() {

    fun getNavigationArgs(collection: MoviesCollection, resources: Resources): Bundle {
        val args = Bundle()
        val title = collection.title
        setTitleToArgs(title, args, resources)
        setItemClassToArgs(args, resources)
        setCollectionIndexToArgs(collection.index, args, resources)
        setFiltersToArgs(collection.filter, args, title, resources)
        return args
    }

    fun convertToTranslatable(collections: List<MoviesCollection>): List<MoviesCollection> {
        val translatableCollections = mutableListOf<MoviesCollection>()
        collections.forEach { collection ->
            val movies =
                if (collection.index == 0) {
                    val premiers = arrayListOf<PremierDto>()
                    collection.movies.forEach { movie ->
                        premiers.add(Converter.convertPremiers(movie as PremierMovie))
                    }
                    premiers
                } else {
                    val notPremiers = arrayListOf<MovieDto>()
                    collection.movies.forEach { movie ->
                        notPremiers.add(Converter.convertMovies(movie as MainMovie))
                    }
                    notPremiers
                }
            val translatableCollection =
                MoviesCollection(collection.index, collection.title, collection.filter, movies)
            translatableCollections.add(translatableCollection)
        }
        return translatableCollections
    }

    private fun setItemClassToArgs(args: Bundle, resources: Resources) {
        val itemsClassName = Movie::class.java.name
        val classKey = resources.getString(ru.skillbox.core.R.string.collection_class_key)
        args.putString(classKey, itemsClassName)
    }

    private fun setCollectionIndexToArgs(index: Int, args: Bundle, resources: Resources) {
        val indexKey = resources.getString(ru.skillbox.core.R.string.collection_index_key)
        args.putInt(indexKey, index)
    }

    private fun setTitleToArgs(title: String, args: Bundle, resources: Resources) {
        val titleKey = resources.getString(ru.skillbox.core.R.string.collection_title_key)
        args.putString(titleKey, title)
    }

    private fun setFiltersToArgs(
        filter: MoviesFilter?,
        args: Bundle,
        title: String,
        resources: Resources
    ) {
        val notExistCode = -1
        val countryId: Int
        val genreId: Int
        if (filter == null) {
            countryId = notExistCode
            genreId = notExistCode
        } else {
            countryId = filter.countryId
            genreId = filter.genreId
        }
        val countryIdKey = resources.getString(ru.skillbox.core.R.string.country_id_key, title)
        val genreIdKey = resources.getString(ru.skillbox.core.R.string.genre_id_key, title)
        args.putInt(countryIdKey, countryId)
        args.putInt(genreIdKey, genreId)
    }
}