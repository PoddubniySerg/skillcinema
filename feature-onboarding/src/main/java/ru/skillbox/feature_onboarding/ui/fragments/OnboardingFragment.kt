package ru.skillbox.feature_onboarding.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import ru.skillbox.core.ui.fragments.BindFragment
import ru.skillbox.feature_onboarding.R
import ru.skillbox.feature_onboarding.databinding.OnboardingFragmentBinding
import ru.skillbox.feature_onboarding.di.OnboardingComponentProvider
import ru.skillbox.feature_onboarding.ui.viewmodels.OnBoardingViewModel
import javax.inject.Inject

internal class OnboardingFragment @Inject constructor() :
    BindFragment<OnboardingFragmentBinding>(OnboardingFragmentBinding::inflate) {

    private val viewModel by activityViewModels<OnBoardingViewModel> {
        (requireContext().applicationContext as OnboardingComponentProvider)
            .onboardingComponent()
            .onboardingViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        runBlocking {
            if (!viewModel.isFirstLaunch()) {
                exitOnboarding()
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.exitFromOnBoardingFlow.onEach { onBoardingIsFinished ->
            if (onBoardingIsFinished) {
                exitOnboarding()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.skipButton.setOnClickListener {
            viewModel.exit()
        }

        viewPagerInit()
    }

    private fun viewPagerInit() {
        val viewPager = binding.viewPager
        val adapter = OnBoardingAdapter(fragment = this)
        viewPager.adapter = adapter
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, _ ->
            tab.view.background =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.on_boarding_dots_selector,
                    null
                )
        }.attach()
    }

    private fun exitOnboarding() {
        findNavController().navigate(R.id.action_onboardingFragment_to_loaderFragment)
    }

    @SuppressLint("DiscouragedApi")
    private class OnBoardingAdapter(
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {

        private val items: List<OnBoardingItem>

        init {
            val onBoardingItems = mutableListOf<OnBoardingItem>()
            val resources = fragment.resources
            val messages = resources.getStringArray(R.array.on_boarding_messages)
            for (i in messages.indices) {
                onBoardingItems.add(
                    i,
                    OnBoardingItem(
                        resources
                            .getIdentifier(
                                "on_boarding_poster_page_${i + 1}",
                                "drawable",
                                fragment.requireContext().packageName
                            ),
                        messages[i]
                    )
                )
            }
            this.items = onBoardingItems
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            return ItemOnboardingFragment.getInstance(
                items[position].posterId,
                items[position].message,
                isLastPage = position == items.size - 1
            )
        }
    }

    private class OnBoardingItem(
        @DrawableRes val posterId: Int,
        val message: String
    )
}