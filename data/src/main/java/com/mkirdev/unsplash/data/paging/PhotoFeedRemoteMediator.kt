package com.mkirdev.unsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mkirdev.unsplash.data.mappers.toKeysDto
import com.mkirdev.unsplash.data.mappers.toPhotoDto
import com.mkirdev.unsplash.data.mappers.toPhotoReactionsDto
import com.mkirdev.unsplash.data.mappers.toReactionsTypeDto
import com.mkirdev.unsplash.data.mappers.toUserDto
import com.mkirdev.unsplash.data.network.photo.api.PhotosApi
import com.mkirdev.unsplash.data.storages.database.dto.base.PhotoDto
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase

private const val ITEMS_PER_PAGE = 10
@OptIn(ExperimentalPagingApi::class)
class PhotoFeedRemoteMediator(
    private val photosApi: PhotosApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PhotoDto>() {

    private val photoDao = appDatabase.photoDao()
    private val reactionsTypeDao = appDatabase.reactionsTypeDao()
    private val photoReactionsDao = appDatabase.photoReactionsDao()
    private val userDao = appDatabase.userDao()
    private val remoteKeysDao = appDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoDto>
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
                    photoFeedNetwork.toKeysDto(prevPage = prevPage, nextPage = nextPage)
                }

                val photos = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toPhotoDto()
                }

                val reactions = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toReactionsTypeDto()
                }

                val photoReactions = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toPhotoReactionsDto()
                }

                val users = response.map { photoFeedNetwork ->
                    photoFeedNetwork.user.toUserDto()
                }

                remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)

                photoDao.addPhotos(photos = photos)
                reactionsTypeDao.addReactionsTypes(reactions = reactions)
                photoReactionsDao.addPhotoReactions(photoReactions = photoReactions)
                userDao.addUsers(users = users)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPagingReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoDto>
    ): RemoteKeysDto? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PhotoDto>
    ): RemoteKeysDto? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { photo ->
                remoteKeysDao.getRemoteKeys(id = photo.id)
            }
    }

    private suspend fun getRemoteKetForLastItem(
        state: PagingState<Int, PhotoDto>
    ): RemoteKeysDto? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { photo ->
                remoteKeysDao.getRemoteKeys(id = photo.id)
            }
    }

}