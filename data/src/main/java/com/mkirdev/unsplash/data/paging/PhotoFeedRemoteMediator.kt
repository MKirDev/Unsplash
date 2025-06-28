package com.mkirdev.unsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mkirdev.unsplash.data.mappers.toKeysEntity
import com.mkirdev.unsplash.data.mappers.toPhotoEntity
import com.mkirdev.unsplash.data.mappers.toPhotoReactionsEntity
import com.mkirdev.unsplash.data.mappers.toReactionsTypeEntity
import com.mkirdev.unsplash.data.mappers.toUserEntity
import com.mkirdev.unsplash.data.network.photos.api.PhotosApi
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase

private const val ITEMS_PER_PAGE = 10
@OptIn(ExperimentalPagingApi::class)
class PhotoFeedRemoteMediator(
    private val photosApi: PhotosApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PhotoFeedDto>() {

    private val photoDao = appDatabase.photoDao()
    private val reactionsTypeDao = appDatabase.reactionsTypeDao()
    private val photoReactionsDao = appDatabase.photoReactionsDao()
    private val userDao = appDatabase.userDao()
    private val remoteKeysDao = appDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoFeedDto>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKetForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = photosApi.getPhotos(page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPagingReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPagingReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoDao.deletePhotos()
                    reactionsTypeDao.deleteReactionsTypes()
                    photoReactionsDao.deletePhotoReactions()
                    userDao.deleteUsers()
                }

                val keys = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toKeysEntity(prevPage = prevPage, nextPage = nextPage)
                }

                val photos = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toPhotoEntity()
                }

                val reactions = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toReactionsTypeEntity()
                }

                val photoReactions = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toPhotoReactionsEntity()
                }

                val users = response.map { photoFeedNetwork ->
                    photoFeedNetwork.user.toUserEntity()
                }

                remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)

                userDao.addUsers(users = users)
                photoDao.addPhotos(photos = photos)
                reactionsTypeDao.addReactionsTypes(reactions = reactions)
                photoReactionsDao.addPhotoReactions(photoReactions = photoReactions)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPagingReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoFeedDto>
    ): RemoteKeysDto? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PhotoFeedDto>
    ): RemoteKeysDto? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { photo ->
                remoteKeysDao.getRemoteKeys(id = photo.id)
            }
    }

    private suspend fun getRemoteKetForLastItem(
        state: PagingState<Int, PhotoFeedDto>
    ): RemoteKeysDto? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { photo ->
                remoteKeysDao.getRemoteKeys(id = photo.id)
            }
    }

}