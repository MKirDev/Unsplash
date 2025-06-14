package com.mkirdev.unsplash.data.repository.photos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.mkirdev.unsplash.data.exceptions.PhotosException
import com.mkirdev.unsplash.data.mappers.toDomain
import com.mkirdev.unsplash.data.mappers.toPhotoEntity
import com.mkirdev.unsplash.data.mappers.toPhotoReactionsEntity
import com.mkirdev.unsplash.data.mappers.toReactionTypeEntity
import com.mkirdev.unsplash.data.mappers.toUserEntity
import com.mkirdev.unsplash.data.network.photos.api.PhotosApi
import com.mkirdev.unsplash.data.network.photos.api.SearchApi
import com.mkirdev.unsplash.data.network.photos.models.list.PhotoFeedNetwork
import com.mkirdev.unsplash.data.paging.PhotoFeedRemoteMediator
import com.mkirdev.unsplash.data.paging.SearchPagingSource
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import com.mkirdev.unsplash.data.storages.datastore.photos.PhotosStorage
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private const val ITEMS_PER_PAGE = 10
private const val SEARCH_TYPE = 1

@OptIn(ExperimentalPagingApi::class)
class PhotosRepositoryImpl(
    private val photosApi: PhotosApi,
    private val searchApi: SearchApi,
    private val appDatabase: AppDatabase,
    private val photosStorage: PhotosStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PhotosRepository {

    override fun getPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PhotoFeedRemoteMediator(
                photosApi = photosApi,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = {
                appDatabase.photoFeedDao().getPhotos()
            }
        ).flow.map { value: PagingData<PhotoFeedDto> ->
            value.map { photoFeedDto -> photoFeedDto.toDomain() }
        }.flowOn(dispatcher).catch {
            throw PhotosException.GetPhotosException(it)
        }
    }

    override fun searchPhotos(query: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(searchApi = searchApi, query = query)
            }
        ).flow.map { value: PagingData<PhotoFeedNetwork> ->
            value.map { photoFeedNetwork -> photoFeedNetwork.toDomain() }
        }.flowOn(dispatcher).catch {
            throw PhotosException.SearchPhotosException(it)
        }
    }

    override suspend fun likePhoto(photoId: String) = withContext(dispatcher) {
        try {
            val photo = appDatabase.photoDao().getPhoto(photoId)
            if (photo != null) {
                appDatabase.reactionsTypeDao().likePhoto(photoId)
                photosStorage.addLikedPhoto(photoId)
            } else {
                val newPhoto = photosApi.getPhotoDetails(photoId)
                appDatabase.withTransaction {
                    appDatabase.photoDao().addPhoto(photo = newPhoto.toPhotoEntity())
                    appDatabase.userDao().addUser(user = newPhoto.user.toUserEntity())
                    appDatabase.reactionsTypeDao()
                        .addReactionType(reaction = newPhoto.toReactionTypeEntity())
                    appDatabase.photoReactionsDao()
                        .addPhotoReaction(photoReactions = newPhoto.toPhotoReactionsEntity())
                    appDatabase.reactionsTypeDao().likePhoto(photoId)
                }
                photosStorage.addLikedPhoto(photoId)
            }
        } catch (t: Throwable) {
            throw PhotosException.LikePhotoException(t)
        }
    }

    override suspend fun unlikePhoto(photoId: String) = withContext(dispatcher) {
        try {
            val photo = appDatabase.photoDao().getPhoto(photoId)
            if (photo != null) {
                if (photo.searchType == SEARCH_TYPE) {
                    appDatabase.withTransaction {
                        appDatabase.reactionsTypeDao().unlikePhoto(photoId)
                        appDatabase.reactionsTypeDao().deleteReactionsType(photoId)
                        appDatabase.photoDao().deletePhoto(photoId)
                        appDatabase.userDao().deleteUser(photo.userId)
                    }
                } else {
                    appDatabase.reactionsTypeDao().unlikePhoto(photoId)
                }
                photosStorage.addUnlikedPhoto(photoId)
            } else {
                photosStorage.addUnlikedPhoto(photoId)
            }
        } catch (t: Throwable) {
            throw PhotosException.UnlikePhotoException(t)
        }
    }

    override suspend fun clearPhotosStorage() {
        try {
            photosStorage.clear()
        } catch (t: Throwable) {
            throw PhotosException.ClearDataException(t)
        }
    }
}