package com.mkirdev.unsplash.data.repository.photos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mkirdev.unsplash.data.mappers.toDomain
import com.mkirdev.unsplash.data.network.photos.api.PhotosApi
import com.mkirdev.unsplash.data.paging.PhotoFeedRemoteMediator
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class PhotosRepositoryImpl(
    private val photosApi: PhotosApi,
    private val appDatabase: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PhotosRepository {

    override suspend fun getPhotos(): Flow<PagingData<Photo>> = withContext(dispatcher) {
        Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = PhotoFeedRemoteMediator(
                photosApi = photosApi,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = {
                appDatabase.photoFeedDao().getPhotos()
            }
        ).flow.map { value: PagingData<PhotoFeedDto> ->
            value.map { photoFeedDto -> photoFeedDto.toDomain() }
        }
    }
}