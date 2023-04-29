package ru.skillbox.feature_onboarding.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCollections()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectionsFlow.collect { nextPage(it) }
            }
        }
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