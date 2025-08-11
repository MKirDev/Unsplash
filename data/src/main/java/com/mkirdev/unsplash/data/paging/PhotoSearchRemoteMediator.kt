package com.mkirdev.unsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mkirdev.unsplash.data.mappers.toKeysSearchEntity
import com.mkirdev.unsplash.data.mappers.toPhotoReactionsSearchEntity
import com.mkirdev.unsplash.data.mappers.toPhotoSearchEntity
import com.mkirdev.unsplash.data.mappers.toReactionsSearchEntity
import com.mkirdev.unsplash.data.mappers.toUserSearchEntity
import com.mkirdev.unsplash.data.network.photos.api.SearchApi
import com.mkirdev.unsplash.data.storages.database.dto.search.PhotoSearchJoinedDto
import com.mkirdev.unsplash.data.storages.database.dto.search.RemoteKeysSearchDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import kotlin.collections.map

private const val ITEMS_PER_PAGE = 10
@OptIn(ExperimentalPagingApi::class)
class PhotoSearchRemoteMediator(
    private val query: String,
    private val searchApi: SearchApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PhotoSearchJoinedDto>() {

    private val photoSearchDao = appDatabase.photoSearchDao()
    private val reactionsSearchDao = appDatabase.reactionsSearchDao()
    private val photoReactionsSearchDao = appDatabase.photoReactionsSearchDao()
    private val userSearchDao = appDatabase.userSearchDao()
    private val remoteKeysSearchDao = appDatabase.remoteKeysSearchDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoSearchJoinedDto>
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
                    photoSearchDao.deletePhotos()
                    photoSearchDao.resetIdSequence()
                    reactionsSearchDao.deleteReactionsTypes()
                    reactionsSearchDao.resetIdSequence()
                    photoReactionsSearchDao.deletePhotoReactions()
                    photoReactionsSearchDao.resetIdSequence()
                    userSearchDao.deleteUsers()
                    userSearchDao.resetIdSequence()
                    remoteKeysSearchDao.deleteAllRemoteKeys()
                    remoteKeysSearchDao.resetIdSequence()
                }

                val keys = response.results.map { photoSearchNetwork ->
                    photoSearchNetwork.toKeysSearchEntity(prevPage = prevPage, nextPage = nextPage)
                }

                val reactions = response.results.map { photoSearchNetwork ->
                    photoSearchNetwork.toReactionsSearchEntity()
                }

                val photoReactions = response.results.map { photoSearchNetwork ->
                    photoSearchNetwork.toPhotoReactionsSearchEntity()
                }

                val users = response.results.map { photoSearchNetwork ->
                    photoSearchNetwork.user.toUserSearchEntity()
                }

                remoteKeysSearchDao.addAllRemoteKeys(remoteKeys = keys)

                userSearchDao.addUsers(users = users)

                val photos = response.results.map { it.toPhotoSearchEntity() }

                photoSearchDao.addPhotos(photos)

                reactionsSearchDao.addReactionsTypes(reactions = reactions)
                photoReactionsSearchDao.addPhotoReactions(photoReactions = photoReactions)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPagingReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoSearchJoinedDto>
    ): RemoteKeysSearchDto? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.photoId?.let { id ->
                remoteKeysSearchDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PhotoSearchJoinedDto>
    ): RemoteKeysSearchDto? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { photo ->
                remoteKeysSearchDao.getRemoteKeys(id = photo.photoId)
            }
    }

    private suspend fun getRemoteKetForLastItem(
        state: PagingState<Int, PhotoSearchJoinedDto>
    ): RemoteKeysSearchDto? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { photo ->
                remoteKeysSearchDao.getRemoteKeys(id = photo.photoId)
            }
    }

}
