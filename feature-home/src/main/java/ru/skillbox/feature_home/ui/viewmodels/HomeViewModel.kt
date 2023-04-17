package ru.skillbox.feature_home.ui.viewmodels

import android.content.res.Resources
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.models.MoviesCollection
import ru.skillbox.core.utils.States
import ru.skillbox.feature_home.domain.GetCollectionsUseCase
import ru.skillbox.feature_home.utils.ArgsProcessor
import ru.skillbox.feature_home.utils.CollectionsProcessor
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val collectionProcessor: CollectionsProcessor,
    private val argsProcessor: ArgsProcessor
) : ViewModel() {

    private var collections: List<MoviesCollection>? = null

    private val _stateMutableStateFlow = MutableStateFlow(States.COMPLETE)
    val states get() = _stateMutableStateFlow.asStateFlow()

    private val _collectionsChannel = Channel<List<MoviesCollection>?>()
    val collectionsFlow get() = _collectionsChannel.receiveAsFlow()

    private val _navigationToListArgsChannel = Channel<Bundle>()
    val navigationToListArgsFlow get() = _navigationToListArgsChannel.receiveAsFlow()

    private val _navigationToFilmPageArgsChannel = Channel<Bundle>()
    val navigationToFilmPageArgsFlow get() = _navigationToFilmPageArgsChannel.receiveAsFlow()

    fun initCollections(args: Bundle?, resources: Resources) {
        args?.let { arguments ->
            this.collections = argsProcessor.getCollectionsFromArgs(arguments, resources)
            if (this.collections != null && this.collections!!.isNotEmpty()) {
                viewModelScope.launch {
                    _collectionsChannel.send(null)
                    _collectionsChannel.send(collections!!)
                }
                return
            }
        }
        val names =
            resources.getStringArray(ru.skillbox.core.R.array.home_movie_collections_names)
        getCollections(names)
    }

    fun onCollectionClick(collection: MoviesCollection, resources: Resources) {
        viewModelScope.launch {
            val args = collectionProcessor.getNavigationArgs(collection, resources)
            _navigationToListArgsChannel.send(args)
        }
    }

    fun onItemClick(movie: Movie, resources: Resources) {
        val keyFilmId = resources.getString(ru.skillbox.core.R.string.film_id_key)
        val args = argsProcessor.getFilmPageArgs(movie, keyFilmId)
        viewModelScope.launch {
            _navigationToFilmPageArgsChannel.send(args)
        }
    }

    private fun getCollections(collectionNames: Array<String>) {
        viewModelScope.launch {
            try {
                _stateMutableStateFlow.value = States.LOADING
                if (collections != null) {
                    _collectionsChannel.send(null)
                    _collectionsChannel.send(collections)
                    return@launch
                }
                val collections = getCollectionsUseCase.execute(collectionNames.asList())
                val translatableCollections = collectionProcessor.convertToTranslatable(collections)
                _collectionsChannel.send(translatableCollections)
            } catch (ex: Exception) {
//            TODO exception handler
            } finally {
                _stateMutableStateFlow.value = States.COMPLETE
            }
        }
    }
}