package ru.skillbox.feature_home.utils

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.models.MoviesCollection
import ru.skillbox.core.domain.models.MoviesCollectionTypes
import ru.skillbox.core.domain.models.MoviesFilter
import ru.skillbox.core.dto.MovieDto
import ru.skillbox.core.dto.PremierDto
import javax.inject.Inject

class ArgsProcessor @Inject constructor() {

    fun getCollectionsFromArgs(args: Bundle, resources: Resources): List<MoviesCollection>? {
        val key = resources.getString(ru.skillbox.core.R.string.get_collection_names_key)
        val titles = args.getStringArrayList(key) ?: return null
        val collections = mutableListOf<MoviesCollection>()
        titles.forEach { title ->
            val filter = getFilter(title, resources, args)
            val index = collections.size
            val className =
                if (index == MoviesCollectionTypes.PREMIERS.index) {
                    PremierDto::class.java
                } else {
                    MovieDto::class.java
                }
            val movies =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    args.getParcelableArrayList(title, className)
                } else {
                    args.getParcelableArrayList(title)
                } ?: emptyList()
            collections.add(MoviesCollection(index, title, filter, movies))
        }
        return collections
    }

    fun getFilmPageArgs(movie: Movie, keyFilmId: String): Bundle {
        val args = Bundle()
        args.putLong(keyFilmId, movie.id.toLong())
        return args
    }

    private fun getFilter(title: String, resources: Resources, args: Bundle): MoviesFilter? {
        val countryIdKey = resources.getString(ru.skillbox.core.R.string.country_id_key, title)
        val genreIdKey = resources.getString(ru.skillbox.core.R.string.genre_id_key, title)
        val countryId = args.getInt(countryIdKey)
        if (countryId < 0) {
            return null
        }
        val genreId = args.getInt(genreIdKey)
        if (genreId < 0) {
            return null
        }
        return MoviesFilter(countryId, genreId)
    }
}