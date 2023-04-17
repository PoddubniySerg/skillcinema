package ru.skillbox.feature_home.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.skillbox.core.ui.adapters.MovieItemAdapter
import ru.skillbox.core.ui.fragments.BindFragment
import ru.skillbox.core.utils.States
import ru.skillbox.core.utils.navigateTo
import ru.skillbox.feature_home.R
import ru.skillbox.feature_home.databinding.HomeFragmentBinding
import ru.skillbox.feature_home.di.HomeComponentProvider
import ru.skillbox.feature_home.ui.adapters.CollectionAdapter
import ru.skillbox.feature_home.ui.viewmodels.HomeViewModel

class HomeFragment : BindFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel by activityViewModels<HomeViewModel> {
        (requireContext().applicationContext as HomeComponentProvider)
            .homeComponent()
            .homeViewModelFactory()
    }
    private lateinit var adapter: CollectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        adapter = initAdapter()
        viewModel.initCollections(arguments, resources)
    }

    private fun initObservers() {
        viewModel.states.onEach { state ->
            setState(state)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.navigationToListArgsFlow.onEach { args ->
            navigateTo(R.id.action_global_list_nav_host, data = args)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.collectionsFlow.onEach { collections ->
            adapter.submitList(collections)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.navigationToFilmPageArgsFlow.onEach { args ->
            navigateTo(R.id.action_global_film_page_navigation, data = args)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter(): CollectionAdapter {
        val showAllItemIcon =
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.movies_item_show_all_button_row_icon,
                null
            )!!
        val adapter =
            CollectionAdapter(
                showAllItemIcon,
                resources.getString(R.string.movies_show_all_item_text_view),
                { viewModel.onItemClick(it, resources) },
                resources.getString(R.string.movies_collection_item_all_button_text),
                MovieItemAdapter.verticalDividerItemDecoration(requireContext())
            ) { viewModel.onCollectionClick(it, resources) }
        binding.moviesList.adapter = adapter
        return adapter
    }

    private fun setState(state: States) {
        when (state) {
            States.LOADING -> binding.progressBar.visibility = View.VISIBLE
            States.COMPLETE -> binding.progressBar.visibility = View.GONE
        }
    }
}