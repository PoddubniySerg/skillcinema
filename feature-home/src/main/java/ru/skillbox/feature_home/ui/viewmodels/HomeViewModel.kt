package ru.skillbox.feature_home.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.core.domain.models.AppCollection
import ru.skillbox.core.dto.MovieDto
import ru.skillbox.core.dto.PremierDto
import ru.skillbox.core.utils.Converter
import ru.skillbox.core.utils.States
import ru.skillbox.feature_home.domain.GetCollectionsUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {

    private var collections: List<AppCollection<Movie>>? = null

    private val _stateMutableStateFlow = MutableStateFlow(States.COMPLETE)
    val states get() = _stateMutableStateFlow.asStateFlow()

    private val _collectionsChannel = Channel<List<AppCollection<Movie>>>()
    val collectionsFlow get() = _collectionsChannel.receiveAsFlow()

    fun getCollections(collectionNames: Array<String>) {
        viewModelScope.launch {
            try {
                _stateMutableStateFlow.value = States.LOADING
                if (collections != null) {
                    _collectionsChannel.send(collections!!)
                    return@launch
                }
                val translatableCollections = mutableListOf<AppCollection<Movie>>()
                getCollectionsUseCase.execute(collectionNames.asList()).forEach { collection ->
                    val movies =
                        if (collection.index == 0) {
                            val premiers = arrayListOf<PremierDto>()
                            collection.movies.forEach { movie ->
                                premiers.add(Converter.convertPremiers(movie as PremierMovie))
                            }
                            premiers
                        } else {
                            val notPremiers = arrayListOf<MovieDto>()
                            collection.movies.forEach { movie ->
                                notPremiers.add(Converter.convertMovies(movie as MainMovie))
                            }
                            notPremiers
                        }
                    val translatableCollection =
                        AppCollection(collection.index, collection.title, collection.filter, movies)
                    translatableCollections.add(translatableCollection as AppCollection<Movie>)
                }
                _collectionsChannel.send(translatableCollections)
            } catch (ex: Exception) {
//            TODO exception handler
            } finally {
                _stateMutableStateFlow.value = States.COMPLETE
            }
        }
    }

    fun setCollections(collections: List<AppCollection<Movie>>) {
        this.collections = collections
        viewModelScope.launch {
            _collectionsChannel.send(collections)
        }
    }
}