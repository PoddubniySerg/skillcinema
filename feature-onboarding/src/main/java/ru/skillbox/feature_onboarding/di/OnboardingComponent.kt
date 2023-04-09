package ru.skillbox.feature_onboarding.di

import dagger.Subcomponent
import ru.skillbox.feature_onboarding.ui.viewmodels.LoaderViewModelFactory
import ru.skillbox.feature_onboarding.ui.viewmodels.OnboardingViewModelFactory
import javax.inject.Scope

@Subcomponent
@OnboardingScope
interface OnboardingComponent {

    fun onboardingViewModelFactory(): OnboardingViewModelFactory

    fun loaderViewModelFactory(): LoaderViewModelFactory
}

interface OnboardingComponentProvider {
    fun onboardingComponent(): OnboardingComponent
}

@Scope
annotation class OnboardingScope