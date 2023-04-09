package ru.skillbox.feature_onboarding.ui.fragments

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.core.domain.models.AppCollection
import ru.skillbox.core.dto.MovieDto
import ru.skillbox.core.dto.PremierDto
import ru.skillbox.core.ui.fragments.BindFragment
import ru.skillbox.core.utils.Converter
import ru.skillbox.core.utils.navigateTo
import ru.skillbox.feature_onboarding.R
import ru.skillbox.feature_onboarding.databinding.LoaderFragmentBinding
import ru.skillbox.feature_onboarding.di.OnboardingComponentProvider
import ru.skillbox.feature_onboarding.ui.viewmodels.LoaderViewModel

internal class LoaderFragment :
    BindFragment<LoaderFragmentBinding>(LoaderFragmentBinding::inflate) {

    private val viewModel by viewModels<LoaderViewModel> {
        (requireContext().applicationContext as OnboardingComponentProvider)
            .onboardingComponent()
            .loaderViewModelFactory()
    }

    override fun onStart() {
        super.onStart()
        getCollections()
        viewModel.collectionsFlow.onEach { collections ->
            nextPage(collections)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getCollections() {
        val names = resources.getStringArray(ru.skillbox.core.R.array.home_movie_collections_names)
        return viewModel.getCollections(names)
    }

    private fun nextPage(collections: List<AppCollection<Movie>>) {
        val args = getArgs(collections)
        navigateTo(R.id.action_loaderFragment_to_homeFragment, data = args)
    }

    private fun getArgs(collections: List<AppCollection<Movie>>): Bundle {
        val key = resources.getString(ru.skillbox.core.R.string.get_collection_names_key)
        val titles = arrayListOf<String>()
        titles.addAll(collections.map { it.title })
        val args = Bundle()
        args.putStringArrayList(key, titles)
        collections.forEach { collection ->
            val filter = collection.filter
            val notExistFilterCode = -1
            val countryIdKey =
                resources.getString(ru.skillbox.core.R.string.country_id_key, collection.title)
            val genreIdKey =
                resources.getString(ru.skillbox.core.R.string.genre_id_key, collection.title)
            if (filter == null) {
                args.putInt(countryIdKey, notExistFilterCode)
                args.putInt(genreIdKey, notExistFilterCode)
            } else {
                args.putInt(countryIdKey, filter.countryId)
                args.putInt(genreIdKey, filter.genreId)
            }
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
        return args
    }
}