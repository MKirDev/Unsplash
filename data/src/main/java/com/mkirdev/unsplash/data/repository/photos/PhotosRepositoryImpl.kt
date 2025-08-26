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
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedJoinedDto
import com.mkirdev.unsplash.data.storages.database.dto.search.PhotoSearchJoinedDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import com.mkirdev.unsplash.data.storages.datastore.photos.PhotosStorage
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
                appDatabase.photoFeedJoinedDao().getFeedJoinedPhotos()
            }
        ).flow.map { value: PagingData<PhotoFeedJoinedDto> ->
            value.map { photoFeedJoinedDto -> photoFeedJoinedDto.toDomain() }
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
                appDatabase.photoSearchJoinedDao().getSearchJoinedPhotos()
            }
        ).flow.map { value: PagingData<PhotoSearchJoinedDto> ->
            value.map { photoSearchJoinedDto -> photoSearchJoinedDto.toDomain() }
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
            photosStorage.addLikedPhoto(photoId)

            val photoFeed = appDatabase.photoFeedDao().getPhoto(photoId)
            val photoSearch = appDatabase.photoSearchDao().getPhoto(photoId)
            val photoCollection = appDatabase.photoCollectionDao().getPhoto(photoId)
            val photoLiked = appDatabase.photoLikedDao().getPhoto(photoId)

            appDatabase.userProfileDao().addLike()

            appDatabase.withTransaction {

                if (photoFeed != null) {
                    val likes = (photoFeed.likes + 1).toString()
                    appDatabase.reactionsFeedDao().likePhoto(photoId)
                    appDatabase.photoFeedDao().updateLikes(likes, photoId)
                }

                if (photoSearch != null) {
                    val likes = (photoSearch.likes + 1).toString()
                    appDatabase.reactionsSearchDao().likePhoto(photoId)
                    appDatabase.photoSearchDao().updateLikes(likes, photoId)
                }

                if (photoCollection != null) {
                    val likes = (photoCollection.likes + 1).toString()
                    appDatabase.reactionsCollectionDao().likePhoto(photoId)
                    appDatabase.photoCollectionDao().updateLikes(likes, photoId)
                }

                if (photoLiked != null) {
                    val likes = (photoLiked.likes + 1).toString()
                    appDatabase.reactionsLikedDao().likePhoto(photoId)
                    appDatabase.photoLikedDao().updateLikes(likes, photoId)
                }
            }
        } catch (t: Throwable) {
            throw PhotosException.LikePhotoLocalException(t)
        }
    }

    override suspend fun unlikePhotoLocal(photoId: String) = withContext(dispatcher) {
        try {
            photosStorage.addUnlikedPhoto(photoId)

            val photoFeed = appDatabase.photoFeedDao().getPhoto(photoId)
            val photoSearch = appDatabase.photoSearchDao().getPhoto(photoId)
            val photoCollection = appDatabase.photoCollectionDao().getPhoto(photoId)
            val photoLiked = appDatabase.photoLikedDao().getPhoto(photoId)


            appDatabase.userProfileDao().removeLike()

            appDatabase.withTransaction {
                if (photoFeed != null) {
                    val likes = (photoFeed.likes - 1).toString()
                    appDatabase.reactionsFeedDao().unlikePhoto(photoId)
                    appDatabase.photoFeedDao().updateLikes(likes, photoId)
                }

                if (photoSearch != null) {
                    val likes = (photoSearch.likes - 1).toString()
                    appDatabase.reactionsSearchDao().unlikePhoto(photoId)
                    appDatabase.photoSearchDao().updateLikes(likes, photoId)
                }

                if (photoCollection != null) {
                    val likes = (photoCollection.likes - 1).toString()
                    appDatabase.reactionsCollectionDao().unlikePhoto(photoId)
                    appDatabase.photoCollectionDao().updateLikes(likes, photoId)
                }

                if (photoLiked != null) {
                    val likes = (photoLiked.likes - 1).toString()
                    appDatabase.reactionsLikedDao().unlikePhoto(photoId)
                    appDatabase.photoLikedDao().updateLikes(likes, photoId)
                }
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

    override suspend fun clearPhotosFromDatabase(): Unit = withContext(dispatcher) {
        try {
            appDatabase.withTransaction {
                appDatabase.apply {
                    userFeedDao().deleteUsers()
                    userSearchDao().deleteUsers()
                    reactionsFeedDao().deleteReactionsTypes()
                    reactionsSearchDao().deleteReactionsTypes()
                    photoFeedDao().deletePhotos()
                    photoSearchDao().deletePhotos()
                    remoteKeysFeedDao().deleteAllRemoteKeys()
                    remoteKeysSearchDao().deleteAllRemoteKeys()
                }
            }
        } catch (t: Throwable) {
            throw PhotosException.ClearPhotosFromDatabase(t)
        }
    }
}