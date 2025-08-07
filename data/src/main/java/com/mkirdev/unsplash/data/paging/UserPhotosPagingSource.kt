package com.mkirdev.unsplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mkirdev.unsplash.data.network.models.list.PhotoFeedNetwork
import com.mkirdev.unsplash.data.network.photos.api.CurrentUserApi

private const val ITEMS_PER_PAGE = 20
class UserPhotosPagingSource(
    private val username: String,
    private val currentUserApi: CurrentUserApi
) : PagingSource<Int, PhotoFeedNetwork>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoFeedNetwork> {
        val currentPage = params.key ?: 1
        return try {
            val response = currentUserApi.getLikedPhotos(username = username, page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.isEmpty()
            if (response.isNotEmpty()) {
                LoadResult.Page(
                    data = response,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
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

    override fun getRefreshKey(state: PagingState<Int, PhotoFeedNetwork>): Int? {
        return state.anchorPosition
    }

}