package ru.skillbox.cinema

import ru.skillbox.cinema.di.AppComponent
import ru.skillbox.cinema.di.DaggerAppComponent
import ru.skillbox.data.DataApp
import ru.skillbox.feature_home.di.HomeComponentProvider
import ru.skillbox.feature_list.di.ListComponentProvider
import ru.skillbox.feature_onboarding.di.OnboardingComponentProvider

class App : DataApp(), OnboardingComponentProvider, HomeComponentProvider, ListComponentProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

    override fun onboardingComponent() = appComponent.onboardingComponent()

    override fun homeComponent() = appComponent.homeComponent()

    override fun listComponent() = appComponent.listComponent()
}