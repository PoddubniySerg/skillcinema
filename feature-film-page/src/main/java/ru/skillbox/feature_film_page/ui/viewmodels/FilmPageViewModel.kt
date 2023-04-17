package ru.skillbox.feature_film_page.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.StaffItem
import ru.skillbox.core.domain.entities.StaffProfessionKey
import ru.skillbox.core.utils.States
import ru.skillbox.feature_film_page.models.TranslatableSeason
import ru.skillbox.feature_film_page.models.TranslatableStaffItem
import ru.skillbox.feature_film_page.usecases.GetMovieDetailsUseCase
import ru.skillbox.feature_film_page.usecases.GetMovieStaffUseCase
import ru.skillbox.feature_film_page.usecases.GetSeasonUseCase
import ru.skillbox.feature_film_page.utils.MovieDetailsProcessor
import ru.skillbox.feature_film_page.utils.SeriesSeasonProcessor
import ru.skillbox.feature_film_page.utils.StaffItemProcessor
import javax.inject.Inject

class FilmPageViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieStaffUseCase: GetMovieStaffUseCase,
    private val getSeasonUseCase: GetSeasonUseCase
) : ViewModel() {

    private val _stateMutableStateFlow = MutableStateFlow(States.COMPLETE)
    val stateFlow get() = _stateMutableStateFlow.asStateFlow()

    private val _movieDetailsChannel = Channel<MovieDetails>()
    val movieDetailsFlow = _movieDetailsChannel.receiveAsFlow()

    private val _seasonsChannel = Channel<List<TranslatableSeason>>()
    val seasonsFlow = _seasonsChannel.receiveAsFlow()

    private val _actorsChannel = Channel<List<TranslatableStaffItem>>()
    val actorsFlow = _actorsChannel.receiveAsFlow()

    private val _staffChannel = Channel<List<TranslatableStaffItem>>()
    val staffFlow = _staffChannel.receiveAsFlow()

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            try {
                _stateMutableStateFlow.value = States.LOADING
                getMovieDescription(id)
                getStaff(id)
//                TODO get gallery
//                TODO get похожие фильмы
            } catch (ex: Exception) {
//            TODO exception handler
                throw ex
            } finally {
                _stateMutableStateFlow.value = States.COMPLETE
            }
        }
    }

    fun getStaffInfo(staffItem: StaffItem) {}

    private suspend fun getMovieDescription(id: Long) {
        val movieDetails = getMovieDetailsUseCase.execute(id)
        val movieDetailsConverted = MovieDetailsProcessor.convertToMovieDetailsDto(movieDetails)
        getSeasons(movieDetailsConverted)
        _movieDetailsChannel.send(movieDetailsConverted)
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
}
