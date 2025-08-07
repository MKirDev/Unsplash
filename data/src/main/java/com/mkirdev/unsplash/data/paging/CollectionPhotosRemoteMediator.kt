package com.mkirdev.unsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mkirdev.unsplash.data.mappers.toKeysCollectionEntity
import com.mkirdev.unsplash.data.mappers.toPhotoCollectionEntity
import com.mkirdev.unsplash.data.mappers.toPhotoReactionsCollectionEntity
import com.mkirdev.unsplash.data.mappers.toReactionsCollectionEntity
import com.mkirdev.unsplash.data.mappers.toUserCollectionEntity
import com.mkirdev.unsplash.data.network.collections.api.CollectionsApi
import com.mkirdev.unsplash.data.storages.database.dto.collection.PhotoCollectionJoinedDto
import com.mkirdev.unsplash.data.storages.database.dto.collection.RemoteKeysCollectionDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase

private const val ITEMS_PER_PAGE = 20

@OptIn(ExperimentalPagingApi::class)
class CollectionPhotosRemoteMediator(
    private val collectionId: String,
    private val collectionsApi: CollectionsApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PhotoCollectionJoinedDto>() {

    private val photoCollectionDao = appDatabase.photoCollectionDao()
    private val reactionsCollectionDao = appDatabase.reactionsCollectionDao()
    private val photoReactionsCollectionDao = appDatabase.photoReactionsCollectionDao()
    private val userCollectionDao = appDatabase.userCollectionDao()

    private val remoteKeysCollectionDao = appDatabase.remoteKeysCollectionDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoCollectionJoinedDto>
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

            val response = collectionsApi.getCollectionPhotos(id = collectionId, page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPagingReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPagingReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoCollectionDao.deletePhotos()
                    reactionsCollectionDao.deleteReactionsTypes()
                    photoReactionsCollectionDao.deletePhotoReactions()
                    userCollectionDao.deleteUsers()
                    remoteKeysCollectionDao.deleteAllRemoteKeys()
                }

                val keys = response.map { photoCollectionNetwork ->
                    photoCollectionNetwork.toKeysCollectionEntity(prevPage = prevPage, nextPage = nextPage)
                }

                val reactions = response.map { photoCollectionNetwork ->
                    photoCollectionNetwork.toReactionsCollectionEntity()
                }

                val photoReactions = response.map { photoCollectionNetwork ->
                    photoCollectionNetwork.toPhotoReactionsCollectionEntity()
                }

                val users = response.map { photoCollectionNetwork ->
                    photoCollectionNetwork.user.toUserCollectionEntity()
                }

                remoteKeysCollectionDao.addAllRemoteKeys(remoteKeys = keys)

                userCollectionDao.addUsers(users = users)

                val photos = response.map { photoCollectionNetwork ->
                    photoCollectionNetwork.toPhotoCollectionEntity(collectionId)
                }

                photoCollectionDao.addPhotos(photos)

                reactionsCollectionDao.addReactionsTypes(reactions = reactions)
                photoReactionsCollectionDao.addPhotoReactions(photoReactions = photoReactions)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPagingReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoCollectionJoinedDto>
    ): RemoteKeysCollectionDto? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.photoId?.let { id ->
                remoteKeysCollectionDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PhotoCollectionJoinedDto>
    ): RemoteKeysCollectionDto? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { photo ->
                remoteKeysCollectionDao.getRemoteKeys(id = photo.photoId)
            }
    }

    private suspend fun getRemoteKetForLastItem(
        state: PagingState<Int, PhotoCollectionJoinedDto>
    ): RemoteKeysCollectionDto? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { photo ->
                remoteKeysCollectionDao.getRemoteKeys(id = photo.photoId)
            }
    }

}
