package com.mkirdev.unsplash.data.repository.photos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.mkirdev.unsplash.data.exceptions.PhotosException
import com.mkirdev.unsplash.data.mappers.toDomain
import com.mkirdev.unsplash.data.network.photos.api.PhotosApi
import com.mkirdev.unsplash.data.network.photos.api.SearchApi
import com.mkirdev.unsplash.data.paging.PhotoFeedRemoteMediator
import com.mkirdev.unsplash.data.paging.PhotoSearchRemoteMediator
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
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

@OptIn(ExperimentalPagingApi::class)
class PhotosRepositoryImpl @Inject constructor(
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
                appDatabase.photoFeedDao().getFeedPhotos()
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
            remoteMediator = PhotoSearchRemoteMediator(
                query = query,
                searchApi = searchApi,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = {
                appDatabase.photoSearchDao().getSearchPhotos()
            }
        ).flow.map { value: PagingData<PhotoFeedDto> ->
            value.map { photoFeedDto -> photoFeedDto.toDomain() }
        }.flowOn(dispatcher).catch {
            throw PhotosException.SearchPhotosException(it)
        }
    }

    override suspend fun getPhoto(id: String): Photo = withContext(dispatcher) {
        try {
            photosApi.getPhoto(id).toDomain()
        } catch (t: Throwable) {
            throw PhotosException.GetPhotoException(t)
        }
    }

    override suspend fun likePhotoLocal(photoId: String) = withContext(dispatcher) {
        try {
            val reactionsTypeDao = appDatabase.reactionsTypeDao()
            val photoDao = appDatabase.photoDao()

            val photo = appDatabase.photoDao().getPhoto(photoId)
            if (photo != null) {
                val likes = (photo.likes + 1).toString()
                appDatabase.withTransaction {
                    reactionsTypeDao.likePhoto(photoId)
                    photoDao.updateLikes(likes, photoId)
                }
                photosStorage.addLikedPhoto(photoId)
            } else {
                photosStorage.addLikedPhoto(photoId)
            }
        } catch (t: Throwable) {
            throw PhotosException.LikePhotoLocalException(t)
        }
    }

    override suspend fun unlikePhotoLocal(photoId: String) = withContext(dispatcher) {
        try {
            val reactionsTypeDao = appDatabase.reactionsTypeDao()
            val photoDao = appDatabase.photoDao()

            val photo = appDatabase.photoDao().getPhoto(photoId)
            if (photo != null) {
                val likes = (photo.likes - 1).toString()
                appDatabase.withTransaction {
                    reactionsTypeDao.unlikePhoto(photoId)
                    photoDao.updateLikes(likes, photoId)
                }
                photosStorage.addUnlikedPhoto(photoId)
            } else {
                photosStorage.addUnlikedPhoto(photoId)
            }
        } catch (t: Throwable) {
            throw PhotosException.UnlikePhotoLocalException(t)
        }
    }

    override suspend fun likePhotoRemote(photoId: String): Unit = withContext(dispatcher) {
        try {
            photosApi.likePhoto(photoId)
        } catch (t: Throwable) {
            throw PhotosException.LikePhotoRemoteException(t)
        }
    }

    override suspend fun unlikePhotoRemote(photoId: String): Unit = withContext(dispatcher) {
        try {
            photosApi.unLikePhoto(photoId)
        } catch (t: Throwable) {
            throw PhotosException.UnlikePhotoRemoteException(t)
        }
    }

    override fun getLikedPhoto(): Flow<String> {
        return photosStorage.getLikedPhoto().flowOn(dispatcher).catch {
            throw PhotosException.GetLikedPhotoException(it)
        }
    }

    override fun getUnlikedPhoto(): Flow<String> {
        return photosStorage.getUnlikedPhoto().flowOn(dispatcher).catch {
            throw PhotosException.GetUnlikedPhotoException(it)
        }
    }

    override suspend fun addDownloadLink(link: String): Unit = withContext(dispatcher) {
        try {
            photosStorage.addDownloadLink(link)
        } catch (t: Throwable) {
            throw PhotosException.AddDownloadLinkException(t)
        }
    }

    override fun getDownloadLink(): Flow<String> {
        return photosStorage.getDownloadLink().flowOn(dispatcher).catch {
            throw PhotosException.GetDownloadLinkException(it)
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