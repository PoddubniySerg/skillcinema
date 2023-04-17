package ru.skillbox.feature_onboarding.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.skillbox.core.domain.models.MoviesCollection
import ru.skillbox.core.ui.fragments.BindFragment
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
    private var collectionsFlow: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCollections()
    }

    override fun onStart() {
        super.onStart()
        this.collectionsFlow = viewModel.collectionsFlow.onEach { collections ->
            nextPage(collections)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onPause() {
        super.onPause()
        this.collectionsFlow?.cancel()
    }

    private fun getCollections() {
        val names = resources.getStringArray(ru.skillbox.core.R.array.home_movie_collections_names)
        return viewModel.getCollections(names)
    }

    private fun nextPage(collections: List<MoviesCollection>) {
        val args = viewModel.getArgs(collections, resources)
        navigateTo(R.id.action_loaderFragment_to_homeFragment, data = args)
    }
}