package ru.skillbox.feature_list.di

import dagger.Subcomponent
import ru.skillbox.feature_list.ui.viewmodels.ListViewModelFactory
import javax.inject.Scope

@Subcomponent
@ListScope
interface ListComponent {

    fun listViewModelFactory(): ListViewModelFactory
}

interface ListComponentProvider {
    fun listComponent(): ListComponent
}

@Scope
annotation class ListScope