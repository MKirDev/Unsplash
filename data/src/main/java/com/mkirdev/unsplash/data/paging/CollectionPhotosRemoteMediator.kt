package com.mkirdev.unsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mkirdev.unsplash.data.mappers.toCollectionEntity
import com.mkirdev.unsplash.data.mappers.toKeysEntity
import com.mkirdev.unsplash.data.mappers.toPhotoCollectionEntity
import com.mkirdev.unsplash.data.mappers.toPhotoEntity
import com.mkirdev.unsplash.data.mappers.toPhotoReactionsEntity
import com.mkirdev.unsplash.data.mappers.toReactionsTypeEntity
import com.mkirdev.unsplash.data.mappers.toUserEntity
import com.mkirdev.unsplash.data.network.collections.api.CollectionsApi
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto
import com.mkirdev.unsplash.data.storages.database.dto.collection.PhotoFromCollectionDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase

private const val ITEMS_PER_PAGE = 20

@OptIn(ExperimentalPagingApi::class)
class CollectionPhotosRemoteMediator(
    private val collectionId: String,
    private val collectionsApi: CollectionsApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PhotoFromCollectionDto>() {

    private val photoDao = appDatabase.photoDao()
    private val reactionsTypeDao = appDatabase.reactionsTypeDao()
    private val photoReactionsDao = appDatabase.photoReactionsDao()
    private val userDao = appDatabase.userDao()

    private val collectionDao = appDatabase.collectionDao()

    private val photoCollectionDao = appDatabase.photoCollectionDao()

    private val remoteKeysDao = appDatabase.remoteKeysCollectionDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoFromCollectionDto>
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

            val collectionInfo = collectionsApi.getCollection(collectionId)
            val response = collectionsApi.getCollectionPhotos(id = collectionId, page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPagingReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPagingReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoCollectionDao.deletePhotoCollection()
                }

                val keys = response.map { photoCollectionNetwork ->
                    photoCollectionNetwork.toKeysEntity(prevPage = prevPage, nextPage = nextPage)
                }

                val reactions = response.map { photoCollectionNetwork ->
                    photoCollectionNetwork.toReactionsTypeEntity()
                }

                val photoReactions = response.map { photoCollectionNetwork ->
                    photoCollectionNetwork.toPhotoReactionsEntity()
                }

                val users = response.map { photoCollectionNetwork ->
                    photoCollectionNetwork.user.toUserEntity()
                }

                val collection = collectionInfo.toCollectionEntity()

                remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)

                userDao.addUsers(users = users)

                collectionDao.addCollection(collection)

                response.forEach { photoCollectionNetwork ->
                    val maxPosition = photoDao.getMaxPosition() ?: 0
                    val photo = photoCollectionNetwork.toPhotoEntity(maxPosition + 1)
                    photoDao.addPhoto(photo)

                    val photoCollection = photoCollectionNetwork.toPhotoCollectionEntity(collection.id)
                    photoCollectionDao.addPhotoCollection(photoCollection)
                }

                reactionsTypeDao.addReactionsTypes(reactions = reactions)
                photoReactionsDao.addPhotoReactions(photoReactions = photoReactions)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPagingReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoFromCollectionDto>
    ): RemoteKeysDto? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PhotoFromCollectionDto>
    ): RemoteKeysDto? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { photo ->
                remoteKeysDao.getRemoteKeys(id = photo.id)
            }
    }

    private suspend fun getRemoteKetForLastItem(
        state: PagingState<Int, PhotoFromCollectionDto>
    ): RemoteKeysDto? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { photo ->
                remoteKeysDao.getRemoteKeys(id = photo.id)
            }
    }

}
