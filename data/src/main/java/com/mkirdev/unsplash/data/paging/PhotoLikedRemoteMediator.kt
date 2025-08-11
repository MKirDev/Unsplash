package com.mkirdev.unsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mkirdev.unsplash.data.mappers.toKeysLikedEntity
import com.mkirdev.unsplash.data.mappers.toPhotoLikedEntity
import com.mkirdev.unsplash.data.mappers.toPhotoReactionsLikedEntity
import com.mkirdev.unsplash.data.mappers.toReactionsLikedEntity
import com.mkirdev.unsplash.data.mappers.toUserLikedEntity
import com.mkirdev.unsplash.data.network.photos.api.CurrentUserApi
import com.mkirdev.unsplash.data.storages.database.dto.liked.RemoteKeysLikedDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import com.mkirdev.unsplash.data.storages.database.view.PhotoLikedJoinedView


private const val ITEMS_PER_PAGE = 10

@OptIn(ExperimentalPagingApi::class)
class PhotoLikedRemoteMediator(
    private val username: String,
    private val currentUserApi: CurrentUserApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PhotoLikedJoinedView>() {

    private val photoLikedDao = appDatabase.photoLikedDao()
    private val reactionsLikedDao = appDatabase.reactionsLikedDao()
    private val photoReactionsLikedDao = appDatabase.photoReactionsLikedDao()
    private val userLikedDao = appDatabase.userLikedDao()
    private val remoteKeysLikedDao = appDatabase.remoteKeysLikedDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoLikedJoinedView>
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

            val response = currentUserApi.getLikedPhotos(username = username, page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPagingReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPagingReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoLikedDao.deletePhotos()
                    photoLikedDao.resetIdSequence()
                    reactionsLikedDao.deleteReactionsTypes()
                    reactionsLikedDao.resetIdSequence()
                    photoReactionsLikedDao.deletePhotoReactions()
                    photoReactionsLikedDao.resetIdSequence()
                    userLikedDao.deleteUsers()
                    userLikedDao.resetIdSequence()
                    remoteKeysLikedDao.deleteAllRemoteKeys()
                    remoteKeysLikedDao.resetIdSequence()
                }

                val keys = response.map { photoLikedNetwork ->
                    photoLikedNetwork.toKeysLikedEntity(prevPage = prevPage, nextPage = nextPage)
                }

                val reactions = response.map { photoLikedNetwork ->
                    photoLikedNetwork.toReactionsLikedEntity()
                }

                val photoReactions = response.map { photoLikedNetwork ->
                    photoLikedNetwork.toPhotoReactionsLikedEntity()
                }

                val users = response.map { photoLikedNetwork ->
                    photoLikedNetwork.user.toUserLikedEntity()
                }

                remoteKeysLikedDao.addAllRemoteKeys(remoteKeys = keys)

                userLikedDao.addUsers(users = users)

                val photos = response.map { it.toPhotoLikedEntity() }
                photoLikedDao.addPhotos(photos)

                reactionsLikedDao.addReactionsTypes(reactions = reactions)
                photoReactionsLikedDao.addPhotoReactions(photoReactions = photoReactions)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPagingReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoLikedJoinedView>
    ): RemoteKeysLikedDto? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.photoId?.let { id ->
                remoteKeysLikedDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PhotoLikedJoinedView>
    ): RemoteKeysLikedDto? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { photo ->
                remoteKeysLikedDao.getRemoteKeys(id = photo.photoId)
            }
    }

    private suspend fun getRemoteKetForLastItem(
        state: PagingState<Int, PhotoLikedJoinedView>
    ): RemoteKeysLikedDto? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { photo ->
                remoteKeysLikedDao.getRemoteKeys(id = photo.photoId)
            }
    }

}