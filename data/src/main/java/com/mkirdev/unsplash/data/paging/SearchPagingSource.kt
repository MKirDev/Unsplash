package com.mkirdev.unsplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mkirdev.unsplash.data.network.photos.api.SearchApi
import com.mkirdev.unsplash.data.network.photos.models.list.PhotoFeedNetwork

private const val FIRST_PAGE = 1
private const val PER_PAGE = 20

class SearchPagingSource(
    private val searchApi: SearchApi,
    private val query: String
) : PagingSource<Int, PhotoFeedNetwork>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoFeedNetwork>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoFeedNetwork> {
        val currentPage = params.key ?: FIRST_PAGE
        return try {
            val response = searchApi.searchPhotos(query = query, page = currentPage, perPage = PER_PAGE)
            val endOfPaginationReached = response.results.isEmpty()
            if (response.results.isNotEmpty()) {
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (currentPage == FIRST_PAGE) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}