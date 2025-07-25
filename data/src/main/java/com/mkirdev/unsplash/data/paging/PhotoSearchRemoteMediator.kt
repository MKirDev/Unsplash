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
import com.mkirdev.unsplash.data.network.photos.api.SearchApi
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import kotlin.collections.forEach

private const val ITEMS_PER_PAGE = 20
@OptIn(ExperimentalPagingApi::class)
class PhotoSearchRemoteMediator(
    private val query: String,
    private val searchApi: SearchApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PhotoFeedDto>() {

    private val photoDao = appDatabase.photoDao()
    private val reactionsTypeDao = appDatabase.reactionsTypeDao()
    private val photoReactionsDao = appDatabase.photoReactionsDao()
    private val userDao = appDatabase.userDao()
    private val remoteKeysDao = appDatabase.remoteKeysSearchDao()

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

            val response = searchApi.searchPhotos(query = query,page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPagingReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPagingReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoDao.deleteSearchPhotos()
                }

                val keys = response.results.map { photoSearchNetwork ->
                    photoSearchNetwork.toKeysEntity(prevPage = prevPage, nextPage = nextPage)
                }

                val reactions = response.results.map { photoSearchNetwork ->
                    photoSearchNetwork.toReactionsTypeEntity()
                }

                val photoReactions = response.results.map { photoSearchNetwork ->
                    photoSearchNetwork.toPhotoReactionsEntity()
                }

                val users = response.results.map { photoSearchNetwork ->
                    photoSearchNetwork.user.toUserEntity()
                }

                remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)

                userDao.addUsers(users = users)

                response.results.forEach { photoSearchNetwork ->
                    val maxPosition = photoDao.getMaxPosition() ?: 0
                    val photo = photoSearchNetwork.toPhotoEntity(maxPosition + 1)
                    photoDao.addPhoto(photo)
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
