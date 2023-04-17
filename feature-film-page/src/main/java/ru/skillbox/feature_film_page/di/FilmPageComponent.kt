package ru.skillbox.feature_film_page.di

import dagger.Subcomponent
import ru.skillbox.feature_film_page.ui.viewmodels.FilmPageViewModelFactory
import javax.inject.Scope

@Subcomponent
@FilmPageScope
interface FilmPageComponent {

    fun filmPageViewModelFactory(): FilmPageViewModelFactory
}

interface FilmPageComponentProvider {
    fun filmPageComponent(): FilmPageComponent
}

@Scope
annotation class FilmPageScope