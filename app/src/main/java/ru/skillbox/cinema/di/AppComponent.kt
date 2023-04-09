package ru.skillbox.cinema.di

import dagger.Component
import ru.skillbox.data.di.DataModule
import ru.skillbox.data.di.DataScope
import ru.skillbox.feature_home.di.HomeComponent
import ru.skillbox.feature_list.di.ListComponent
import ru.skillbox.feature_onboarding.di.OnboardingComponent

@Component(modules = [DataModule::class])
@DataScope
interface AppComponent {

    fun onboardingComponent(): OnboardingComponent

    fun homeComponent(): HomeComponent

    fun listComponent(): ListComponent
}