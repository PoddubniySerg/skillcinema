package ru.skillbox.feature_list.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.utils.States
import ru.skillbox.feature_list.domain.usecases.GetMoviesByPageUseCase
import ru.skillbox.feature_list.models.GetMoviesByPageParams
import ru.skillbox.feature_list.models.ListItem
import ru.skillbox.feature_list.models.MovieItem
import ru.skillbox.feature_list.ui.sources.ListPagingSource
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val getMoviesByPageUseCase: GetMoviesByPageUseCase
) : ViewModel() {

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_SIZE = 1
    }

    private val _stateMutableStateFlow = MutableStateFlow(States.COMPLETE)
    val states get() = _stateMutableStateFlow.asStateFlow()

    private lateinit var pager: Pager<Int, ListItem>
    val pagedMovies: Flow<PagingData<ListItem>> get() = pager.flow.cachedIn(viewModelScope)

    fun start(type: Class<*>, params: Any) {
        val getData: suspend (Int) -> List<Any>
        when (type) {
            Movie::class.java ->
                getData = { page -> getMovies(page, params as GetMoviesByPageParams) }
            else -> throw RuntimeException("Unknown data type")
        }
        pager =
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = {
                    ListPagingSource(
                        FIRST_PAGE,
                        getMoviesByPage = getData
                    )
                }
            )
        try {
        } catch (ex: Exception) {
//            TODO exception handler
        }
    }

    private suspend fun getMovies(page: Int, params: GetMoviesByPageParams): List<MovieItem> {
        return try {
            if (page > 1) {
                _stateMutableStateFlow.value = States.LOADING
            }
            getMoviesByPageUseCase.execute(params.moviesType, page, params.filter)
        } catch (ex: Exception) {
//            TODO exception handler
            emptyList()
        } finally {
            _stateMutableStateFlow.value = States.COMPLETE
        }
    }
}