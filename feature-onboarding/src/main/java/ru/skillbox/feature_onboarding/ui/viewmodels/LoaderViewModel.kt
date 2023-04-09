package ru.skillbox.feature_onboarding.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.models.AppCollection
import ru.skillbox.feature_onboarding.domain.usecases.GetCollectionsUseCase
import javax.inject.Inject

class LoaderViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {

    private val _collectionsFlow = Channel<List<AppCollection<Movie>>>()
    val collectionsFlow = _collectionsFlow.receiveAsFlow()

    fun getCollections(collectionNames: Array<String>) {
        viewModelScope.launch {
            try {
                val collections = getCollectionsUseCase.execute(collectionNames.asList())
                _collectionsFlow.send(collections)
            } catch (ex: Exception) {
//            TODO exception handler
            }
        }
    }
}