package ru.skillbox.feature_onboarding.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.annotation.DrawableRes
import androidx.fragment.app.activityViewModels
import ru.skillbox.core.ui.fragments.BindFragment
import ru.skillbox.feature_onboarding.databinding.OnboardingFragmentItemBinding
import ru.skillbox.feature_onboarding.di.OnboardingComponentProvider
import ru.skillbox.feature_onboarding.ui.viewmodels.OnBoardingViewModel
import javax.inject.Inject

internal class ItemOnboardingFragment @Inject constructor() :
    BindFragment<OnboardingFragmentItemBinding>(OnboardingFragmentItemBinding::inflate) {

    companion object {
        private const val IMAGE_ID_KEY = "image_id"
        private const val MESSAGE_KEY = "message"
        private const val IS_LAST_PAGE_KEY = "is_last_page"
        private const val MIN_DIFF_SWIPE = 25f
        private const val START_X_SWIPE = -1f

        fun getInstance(
            @DrawableRes posterId: Int,
            message: String,
            isLastPage: Boolean
        ): ItemOnboardingFragment {
            val fragment = ItemOnboardingFragment()
            val args = Bundle().apply {
                putInt(IMAGE_ID_KEY, posterId)
                putString(MESSAGE_KEY, message)
                putBoolean(IS_LAST_PAGE_KEY, isLastPage)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel by activityViewModels<OnBoardingViewModel> {
        (requireContext().applicationContext as OnboardingComponentProvider)
            .onboardingComponent()
            .onboardingViewModelFactory()
    }

    private var startX = START_X_SWIPE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getBoolean(IS_LAST_PAGE_KEY) == true) setLeftSwipeListener()
        binding.poster.setImageResource(requireArguments().getInt(IMAGE_ID_KEY))
        binding.message.text = requireArguments().getString(MESSAGE_KEY)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setLeftSwipeListener() {
        binding.root.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_CANCEL -> {
                    if (startX >= 0 && (startX - event.x) > MIN_DIFF_SWIPE) {
                        viewModel.exit()
                    }
                }
            }
            return@setOnTouchListener false
        }
    }
}