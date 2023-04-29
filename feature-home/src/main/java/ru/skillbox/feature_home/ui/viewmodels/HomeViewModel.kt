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
import ru.skillbox.core.utils.RequiredCollections
import ru.skillbox.core.utils.States
import ru.skillbox.feature_home.domain.GetCollectionsUseCase
import ru.skillbox.feature_home.domain.SetMoviesByCollectionUseCase
import ru.skillbox.feature_home.utils.ArgsProcessor
import ru.skillbox.feature_home.utils.CollectionsProcessor
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val collectionProcessor: CollectionsProcessor,
    private val argsProcessor: ArgsProcessor,
    private val setMoviesByCollectionUseCase: SetMoviesByCollectionUseCase
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
        viewModelScope.launch {
            try {
                _stateMutableStateFlow.value = States.LOADING
                processArgs(args, resources)
                if (collections == null || collections!!.isEmpty()) {
                    getCollections(resources)
                }
                sendCollection()
            } catch (ex: Exception) {
//            TODO exception handler
            } finally {
                _stateMutableStateFlow.value = States.COMPLETE
            }
        }
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

    private fun processArgs(args: Bundle?, resources: Resources) {
        args?.let { arguments ->
            this.collections = argsProcessor.getCollectionsFromArgs(arguments, resources)
        }
    }

    private suspend fun getCollections(resources: Resources) {
        val collectionNames =
            resources.getStringArray(ru.skillbox.core.R.array.home_movie_collections_names)
        val collectionList = getCollectionsUseCase.execute(collectionNames.asList())
        collections = collectionProcessor.convertToTranslatable(collectionList)
    }

    private suspend fun sendCollection() {
        val collection = RequiredCollections.VIEWED_COLLECTION.name
        collections!!.forEach { setMoviesByCollectionUseCase.execute(it.movies, collection) }
        _collectionsChannel.send(null)
        _collectionsChannel.send(collections!!)
    }
}