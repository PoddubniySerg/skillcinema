package ru.skillbox.feature_list.ui.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.skillbox.feature_list.models.ListItem

class ListPagingSource(
    private val firstPage: Int,
    private val getMoviesByPage: suspend (Int) -> List<ListItem>
) : PagingSource<Int, ListItem>() {

    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int = firstPage

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            getMoviesByPage(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }
}