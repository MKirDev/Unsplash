package com.mkirdev.unsplash.data.repository.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mkirdev.unsplash.data.exceptions.SearchException
import com.mkirdev.unsplash.data.mappers.toDomain
import com.mkirdev.unsplash.data.network.photos.api.SearchApi
import com.mkirdev.unsplash.data.paging.PhotoSearchRemoteMediator
import com.mkirdev.unsplash.data.storages.database.dto.search.PhotoSearchJoinedDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

@OptIn(ExperimentalPagingApi::class)
class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val appDatabase: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SearchRepository {

    override fun searchPhotos(query: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PhotoSearchRemoteMediator(
                query = query,
                searchApi = searchApi,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = {
                appDatabase.photoSearchJoinedDao().getSearchJoinedPhotos()
            }
        ).flow.map { value: PagingData<PhotoSearchJoinedDto> ->
            value.map { photoSearchJoinedDto -> photoSearchJoinedDto.toDomain() }
        }.flowOn(dispatcher).catch {
            throw SearchException.SearchPhotosException(it)
        }
    }
}