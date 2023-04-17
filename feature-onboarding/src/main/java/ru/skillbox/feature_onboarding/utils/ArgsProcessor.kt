package ru.skillbox.feature_onboarding.utils

import android.content.res.Resources
import android.os.Bundle
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.core.domain.models.MoviesCollection
import ru.skillbox.core.domain.models.MoviesFilter
import ru.skillbox.core.dto.MovieDto
import ru.skillbox.core.dto.PremierDto
import ru.skillbox.core.utils.Converter
import javax.inject.Inject

class ArgsProcessor @Inject constructor() {

    private val notExistFilterCode = -1

    fun getArgs(collections: List<MoviesCollection>, resources: Resources): Bundle {
        val args = Bundle()
        setTitles(collections, resources, args)
        collections.forEach { collection ->
            val filter = collection.filter
            setFilter(filter, args, resources, collection.title)
            setMovies(collection, args)
        }
        return args
    }

    private fun setTitles(collections: List<MoviesCollection>, resources: Resources, args: Bundle) {
        val key = resources.getString(ru.skillbox.core.R.string.get_collection_names_key)
        val titles = arrayListOf<String>()
        titles.addAll(collections.map { it.title })
        args.putStringArrayList(key, titles)
    }

    private fun setFilter(
        filter: MoviesFilter?,
        args: Bundle,
        resources: Resources,
        title: String
    ) {
        val countryIdKey =
            resources.getString(ru.skillbox.core.R.string.country_id_key, title)
        val genreIdKey =
            resources.getString(ru.skillbox.core.R.string.genre_id_key, title)
        if (filter == null) {
            args.putInt(countryIdKey, notExistFilterCode)
            args.putInt(genreIdKey, notExistFilterCode)
        } else {
            args.putInt(countryIdKey, filter.countryId)
            args.putInt(genreIdKey, filter.genreId)
        }
    }

    private fun setMovies(collection: MoviesCollection, args: Bundle) {
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
        args.putParcelableArrayList(collection.title, movies)
    }
}