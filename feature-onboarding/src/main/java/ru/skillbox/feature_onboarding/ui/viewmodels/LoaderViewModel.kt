package ru.skillbox.feature_onboarding.ui.viewmodels

import android.content.res.Resources
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.skillbox.core.domain.models.MoviesCollection
import ru.skillbox.feature_onboarding.domain.usecases.GetCollectionsUseCase
import ru.skillbox.feature_onboarding.utils.ArgsProcessor
import javax.inject.Inject

class LoaderViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val argsProcessor: ArgsProcessor
) : ViewModel() {

    private val _collectionsFlow = Channel<List<MoviesCollection>>()
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

    fun getArgs(collections: List<MoviesCollection>, resources: Resources): Bundle {
        return argsProcessor.getArgs(collections, resources)
    }
}