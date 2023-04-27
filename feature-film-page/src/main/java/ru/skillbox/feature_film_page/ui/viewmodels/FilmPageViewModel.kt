package ru.skillbox.feature_film_page.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.StaffProfessionKey
import ru.skillbox.core.utils.RequiredCollections
import ru.skillbox.core.utils.States
import ru.skillbox.feature_film_page.models.MovieImagesGallery
import ru.skillbox.feature_film_page.models.TranslatableMoviesCollection
import ru.skillbox.feature_film_page.models.TranslatableSeason
import ru.skillbox.feature_film_page.models.TranslatableStaffItem
import ru.skillbox.feature_film_page.usecases.GetMovieCollectionsUseCase
import ru.skillbox.feature_film_page.usecases.GetMovieDetailsUseCase
import ru.skillbox.feature_film_page.usecases.GetMovieGalleryUseCase
import ru.skillbox.feature_film_page.usecases.GetMovieStaffUseCase
import ru.skillbox.feature_film_page.usecases.GetRelatedMoviesUseCase
import ru.skillbox.feature_film_page.usecases.GetSeasonUseCase
import ru.skillbox.feature_film_page.usecases.SetFavouriteUseCase
import ru.skillbox.feature_film_page.usecases.SetViewedUseCase
import ru.skillbox.feature_film_page.usecases.SetWillViewUseCase
import ru.skillbox.feature_film_page.utils.MovieDetailsProcessor
import ru.skillbox.feature_film_page.utils.RelatedMovieProcessor
import ru.skillbox.feature_film_page.utils.SeriesSeasonProcessor
import ru.skillbox.feature_film_page.utils.StaffItemProcessor
import javax.inject.Inject

class FilmPageViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieStaffUseCase: GetMovieStaffUseCase,
    private val getSeasonUseCase: GetSeasonUseCase,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val getRelatedMoviesUseCase: GetRelatedMoviesUseCase,
    private val setFavouriteUseCase: SetFavouriteUseCase,
    private val setWillViewUseCase: SetWillViewUseCase,
    private val setViewedUseCase: SetViewedUseCase,
    private val getMovieCollectionsUseCase: GetMovieCollectionsUseCase
) : ViewModel() {

    private var movieDetails: MovieDetails? = null

    private val _stateMutableStateFlow = MutableStateFlow(States.COMPLETE)
    val stateFlow get() = _stateMutableStateFlow.asStateFlow()

    private val _movieDetailsChannel = Channel<MovieDetails>()
    val movieDetailsFlow = _movieDetailsChannel.receiveAsFlow()

    private val _actorsChannel = Channel<List<TranslatableStaffItem>>()
    val actorsFlow = _actorsChannel.receiveAsFlow()

    private val _staffChannel = Channel<List<TranslatableStaffItem>>()
    val staffFlow = _staffChannel.receiveAsFlow()

    private val _seasonsChannel = Channel<List<TranslatableSeason>>()
    val seasonsFlow = _seasonsChannel.receiveAsFlow()

    private val _galleryChannel = Channel<MovieImagesGallery>()
    val galleryFlow = _galleryChannel.receiveAsFlow()

    private val _relatedMoviesChannel = Channel<TranslatableMoviesCollection>()
    val relatedMoviesFlow = _relatedMoviesChannel.receiveAsFlow()

    private val _isMovieFavouriteChannel = Channel<Boolean>()
    val isMovieFavouriteFlow = _isMovieFavouriteChannel.receiveAsFlow()

    private val _isMovieWillViewChannel = Channel<Boolean>()
    val isMovieWillViewFlow = _isMovieWillViewChannel.receiveAsFlow()

    private val _isMovieViewedChannel = Channel<Boolean>()
    val isMovieViewedFlow = _isMovieViewedChannel.receiveAsFlow()

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            try {
                _stateMutableStateFlow.value = States.LOADING
                getMovieDescription(id)
                getStaff(id)
                getGallery(id)
                getRelatedMovies(id)
            } catch (ex: Exception) {
//            TODO exception handler
                throw ex
            } finally {
                _stateMutableStateFlow.value = States.COMPLETE
            }
        }
    }

    fun setFavourite() {
        if (movieDetails != null) {
            viewModelScope.launch {
                val isMovieFavourite = setFavouriteUseCase.execute(movieDetails!!)
                _isMovieFavouriteChannel.send(isMovieFavourite)
            }
        }
    }

    fun setWillView() {
        if (movieDetails != null) {
            viewModelScope.launch {
                val isMovieWillView = setWillViewUseCase.execute(movieDetails!!)
                _isMovieWillViewChannel.send(isMovieWillView)
            }
        }
    }

    fun setViewed() {
        if (movieDetails != null) {
            viewModelScope.launch {
                val isMovieViewed = setViewedUseCase.execute(movieDetails!!)
                _isMovieViewedChannel.send(isMovieViewed)
            }
        }
    }

    private suspend fun getMovieDescription(id: Long) {
        movieDetails = getMovieDetailsUseCase.execute(id)
        val movieDetailsConverted = MovieDetailsProcessor.convertToMovieDetailsDto(movieDetails!!)
        val collections = getMovieCollectionsUseCase.execute(id)
        getSeasons(movieDetailsConverted)
        _movieDetailsChannel.send(movieDetailsConverted)
        _isMovieFavouriteChannel.send(collections.contains(RequiredCollections.FAVOURITE_COLLECTION.name))
        _isMovieWillViewChannel.send(collections.contains(RequiredCollections.WILL_VIEW_COLLECTION.name))
        _isMovieViewedChannel.send(collections.contains(RequiredCollections.VIEWED_COLLECTION.name))
    }

    private suspend fun getStaff(id: Long) {
        val totalStaffList = getMovieStaffUseCase.execute(id)
        val actorList = ArrayList<TranslatableStaffItem>()
        val staffList = ArrayList<TranslatableStaffItem>()
        totalStaffList.forEach { item ->
            val convertedItem = StaffItemProcessor.convertToTranslatable(item)
            if (item.professionKey == StaffProfessionKey.ACTOR) {
                actorList.add(convertedItem)
            } else {
                staffList.add(convertedItem)
            }
        }
        _actorsChannel.send(actorList)
        _staffChannel.send(staffList)
    }

    private suspend fun getSeasons(movieDetails: MovieDetails) {
        val isSerial = movieDetails.isSerial
        if (isSerial == null || !isSerial) {
            _seasonsChannel.send(emptyList())
            return
        }
        val seasons = getSeasonUseCase.execute(movieDetails.id)
        val translatableSeasons =
            seasons.map { SeriesSeasonProcessor.convertToTranslatableSeason(it) }
        _seasonsChannel.send(translatableSeasons)
    }

    private suspend fun getGallery(filmId: Long) {
        val gallery = getMovieGalleryUseCase.execute(filmId)
        _galleryChannel.send(gallery)
    }

    private suspend fun getRelatedMovies(filmId: Long) {
        val relatedMovies = getRelatedMoviesUseCase.execute(filmId)
        val translatableRelatedMovies = RelatedMovieProcessor.convertToTranslatable(relatedMovies)
        _relatedMoviesChannel.send(translatableRelatedMovies)
    }
}
