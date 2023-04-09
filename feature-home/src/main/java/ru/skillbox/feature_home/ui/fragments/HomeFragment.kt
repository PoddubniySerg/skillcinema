package ru.skillbox.feature_home.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.models.*
import ru.skillbox.core.dto.MovieDto
import ru.skillbox.core.dto.PremierDto
import ru.skillbox.core.ui.adapters.CollectionAdapter
import ru.skillbox.core.ui.adapters.MovieItemAdapter
import ru.skillbox.core.ui.fragments.BindFragment
import ru.skillbox.core.utils.Constants
import ru.skillbox.core.utils.States
import ru.skillbox.core.utils.navigateTo
import ru.skillbox.feature_home.R
import ru.skillbox.feature_home.databinding.HomeFragmentBinding
import ru.skillbox.feature_home.di.HomeComponentProvider
import ru.skillbox.feature_home.ui.viewmodels.HomeViewModel

class HomeFragment : BindFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel by activityViewModels<HomeViewModel> {
        (requireContext().applicationContext as HomeComponentProvider)
            .homeComponent()
            .homeViewModelFactory()
    }
    private lateinit var adapter: CollectionAdapter<Movie, RecyclerView.ViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        adapter = initAdapter()
        initCollections()
    }

    private fun initObservers() {
        viewModel.states.onEach { setState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.collectionsFlow.onEach { submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter(): CollectionAdapter<Movie, RecyclerView.ViewHolder> {
        val managerFactory = MoviesManagerFactory(requireContext())
        val adapterFactory = MoviesAdapterFactory { onItemPosterClick(it) }
        val adapter =
            CollectionAdapter(
                managerFactory,
                adapterFactory,
                { view, count -> setAllButtonText(view, count) },
                MovieItemAdapter.verticalDividerItemDecoration(requireContext()),
                { onClickAllButton(it) }
            )
        binding.moviesList.adapter = adapter
        return adapter
    }

    private fun initCollections() {
        val key = resources.getString(ru.skillbox.core.R.string.get_collection_names_key)
        val titles = arguments?.getStringArrayList(key)
        if (titles == null) {
            getCollections()
            return
        }
        val collections = mutableListOf<AppCollection<Movie>>()
        titles.forEach { title ->
            val filter = getFilter(title)
            val index = collections.size
            val className =
                if (index == MoviesCollectionTypes.PREMIERS.index) {
                    PremierDto::class.java
                } else {
                    MovieDto::class.java
                }
            val movies =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelableArrayList(title, className)
                } else {
                    arguments?.getParcelableArrayList(title)
                } ?: emptyList()
            collections.add(AppCollection(index, title, filter, movies))
        }
        viewModel.setCollections(collections)
    }

    private fun getCollections() {
        val names = resources.getStringArray(ru.skillbox.core.R.array.home_movie_collections_names)
        viewModel.getCollections(names)
    }

    private fun getFilter(title: String): MoviesFilter? {
        val countryIdKey = resources.getString(ru.skillbox.core.R.string.country_id_key, title)
        val genreIdKey = resources.getString(ru.skillbox.core.R.string.genre_id_key, title)
        val countryId = requireArguments().getInt(countryIdKey)
        if (countryId < 0) {
            return null
        }
        val genreId = requireArguments().getInt(genreIdKey)
        if (genreId < 0) {
            return null
        }
        return MoviesFilter(countryId, genreId)
    }

    private fun onItemPosterClick(movie: Movie) {
//        todo implement
    }

    private fun setAllButtonText(view: AppCompatButton, countItems: Int) {
        if (countItems < Constants.MAX_MOVIES_COLLECTION_SIZE) {
            view.visibility = View.GONE
        } else {
            view.text = resources.getString(R.string.movies_collection_item_all_button_text)
        }
    }

    private fun onClickAllButton(collection: AppCollection<Movie>) {
        val args = Bundle()
        val title = collection.title
        setTitleToArgs(title, args)
        setItemClassToArgs(args)
        setCollectionIndexToArgs(collection.index, args)
        setFiltersToArgs(collection.filter, args, title)
        navigateTo(R.id.action_global_list_nav_host, data = args)
    }

    private fun setItemClassToArgs(args: Bundle) {
        val itemsClassName = Movie::class.java.name
        val classKey = resources.getString(ru.skillbox.core.R.string.collection_class_key)
        args.putString(classKey, itemsClassName)
    }

    private fun setCollectionIndexToArgs(index: Int, args: Bundle) {
        val indexKey = resources.getString(ru.skillbox.core.R.string.collection_index_key)
        args.putInt(indexKey, index)
    }

    private fun setTitleToArgs(title: String, args: Bundle) {
        val titleKey = resources.getString(ru.skillbox.core.R.string.collection_title_key)
        args.putString(titleKey, title)
    }

    private fun setFiltersToArgs(filter: MoviesFilter?, args: Bundle, title: String) {
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

    private fun setState(state: States) {
        when (state) {
            States.LOADING -> binding.progressBar.visibility = View.VISIBLE
            States.COMPLETE -> binding.progressBar.visibility = View.GONE
        }
    }

    private fun submitList(collections: List<AppCollection<Movie>>) {
        adapter.submitList(collections)
    }
}