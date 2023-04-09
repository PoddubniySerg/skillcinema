package ru.skillbox.feature_home.di

import dagger.Subcomponent
import ru.skillbox.feature_home.ui.viewmodels.HomeViewModelFactory
import javax.inject.Scope

@Subcomponent
@HomeScope
interface HomeComponent {

    fun homeViewModelFactory(): HomeViewModelFactory
}

interface HomeComponentProvider {
    fun homeComponent(): HomeComponent
}

@Scope
annotation class HomeScope