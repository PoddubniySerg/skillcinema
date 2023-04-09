package ru.skillbox.feature_list.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.models.MoviesCollectionTypes
import ru.skillbox.core.domain.models.MoviesFilter
import ru.skillbox.core.ui.fragments.BindFragment
import ru.skillbox.core.utils.States
import ru.skillbox.feature_list.R
import ru.skillbox.feature_list.databinding.FragmentListPageBinding
import ru.skillbox.feature_list.di.ListComponentProvider
import ru.skillbox.feature_list.models.GetMoviesByPageParams
import ru.skillbox.feature_list.ui.adapters.ListPagingAdapter
import ru.skillbox.feature_list.ui.viewmodels.ListViewModel
import javax.inject.Inject

class ListPageFragment @Inject constructor() :
    BindFragment<FragmentListPageBinding>(FragmentListPageBinding::inflate) {

    private val viewModel by activityViewModels<ListViewModel> {
        (requireContext().applicationContext as ListComponentProvider)
            .listComponent()
            .listViewModelFactory()
    }
    private lateinit var adapter: ListPagingAdapter
    private lateinit var dataType: Class<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initToolbar()
        initRecyclerView()
        initSwipeRefresh()
        getDataParams()
        initObservers()
    }

    private fun initAdapter() {
        val classKey = resources.getString(ru.skillbox.core.R.string.collection_class_key)
        val className =
            arguments?.getString(classKey) ?: throw RuntimeException("ClassName not found")
        dataType = Class.forName(className)
        adapter = ListPagingAdapter(dataType) { item -> onItemClick(item) }
    }

    private fun initToolbar() {
        val toolbar = binding.listPageToolbar
        toolbar.setupWithNavController(findNavController())
        toolbar.title = null
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_icon)
        val titleKey = resources.getString(ru.skillbox.core.R.string.collection_title_key)
        val title = arguments?.getString(titleKey)
        binding.toolbarTitle.text = title
    }

    private fun initRecyclerView() {
        val verticalDividerItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        verticalDividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                ru.skillbox.core.R.drawable.collection_divider_vertical,
                requireContext().theme
            )!!
        )

        val horizontalDividerItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
        horizontalDividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                ru.skillbox.core.R.drawable.collection_divider_horizontal,
                requireContext().theme
            )!!
        )

        val recyclerView = binding.moviesRecyclerView
        recyclerView.addItemDecoration(verticalDividerItemDecoration)
        recyclerView.addItemDecoration(horizontalDividerItemDecoration)
        recyclerView.adapter = adapter
    }

    private fun initSwipeRefresh() {
        val swipeRefresh = binding.swipeRefresh
        swipeRefresh.setColorSchemeResources(ru.skillbox.core.R.color.progress_bar_color)
        swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }


    private fun initObservers() {
        viewModel.states.onEach { setState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pagedMovies.onEach { pagingData ->
            adapter.submitData(pagingData)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.loadStateFlow.onEach {
            val state = it.refresh
            binding.swipeRefresh.isRefreshing = state == LoadState.Loading
            when (state) {
                is LoadState.Error -> {
                    Toast.makeText(activity, state.error.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
//                    TODO nothing
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getDataParams() {
        val args = requireArguments()
        when (dataType) {
            Movie::class.java -> startMovies(args)
        }
    }

    private fun startMovies(args: Bundle) {
        val titleKey = resources.getString(ru.skillbox.core.R.string.collection_title_key)
        val title = args.getString(titleKey)
        val filter = getFilter(args, title)
        val moviesType =
            if (filter == null) {
                getMoviesType(args)
            } else {
                null
            }
        val params = GetMoviesByPageParams(moviesType, filter)
        viewModel.start(dataType, params)
    }

    private fun getMoviesType(args: Bundle): MoviesCollectionTypes? {
        val indexKey = resources.getString(ru.skillbox.core.R.string.collection_index_key)
        val indexCollection = args.getInt(indexKey)
        MoviesCollectionTypes.values().forEach { type ->
            if (type.index == indexCollection) {
                return type
            }
        }
        return null
    }

    private fun getFilter(args: Bundle, title: String?): MoviesFilter? {
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

    private fun setState(state: States) {
        when (state) {
            States.LOADING -> binding.progressBar.visibility = View.VISIBLE
            States.COMPLETE -> binding.progressBar.visibility = View.GONE
        }
    }

    private fun onItemClick(item: Any) {}
}