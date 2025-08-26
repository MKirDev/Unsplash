package com.mkirdev.unsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mkirdev.unsplash.data.mappers.toKeysFeedEntity
import com.mkirdev.unsplash.data.mappers.toPhotoFeedEntity
import com.mkirdev.unsplash.data.mappers.toPhotoReactionsFeedEntity
import com.mkirdev.unsplash.data.mappers.toReactionsFeedEntity
import com.mkirdev.unsplash.data.mappers.toUserFeedEntity
import com.mkirdev.unsplash.data.network.photos.api.PhotosApi
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedJoinedDto
import com.mkirdev.unsplash.data.storages.database.dto.feed.RemoteKeysFeedDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase

private const val ITEMS_PER_PAGE = 20
@OptIn(ExperimentalPagingApi::class)
class PhotoFeedRemoteMediator(
    private val photosApi: PhotosApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PhotoFeedJoinedDto>() {

    private val photoFeedDao = appDatabase.photoFeedDao()
    private val reactionsFeedDao = appDatabase.reactionsFeedDao()
    private val photoReactionsFeedDao = appDatabase.photoReactionsFeedDao()
    private val userFeedDao = appDatabase.userFeedDao()
    private val remoteKeysFeedDao = appDatabase.remoteKeysFeedDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoFeedJoinedDto>
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
                    photoFeedDao.deletePhotos()
                    photoFeedDao.resetIdSequence()
                    reactionsFeedDao.deleteReactionsTypes()
                    reactionsFeedDao.resetIdSequence()
                    photoReactionsFeedDao.deletePhotoReactions()
                    photoReactionsFeedDao.resetIdSequence()
                    userFeedDao.deleteUsers()
                    userFeedDao.resetIdSequence()
                    remoteKeysFeedDao.deleteAllRemoteKeys()
                    remoteKeysFeedDao.resetIdSequence()
                }

                val keys = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toKeysFeedEntity(prevPage = prevPage, nextPage = nextPage)
                }

                val reactions = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toReactionsFeedEntity()
                }

                val photoReactions = response.map { photoFeedNetwork ->
                    photoFeedNetwork.toPhotoReactionsFeedEntity()
                }

                val users = response.map { photoFeedNetwork ->
                    photoFeedNetwork.user.toUserFeedEntity()
                }

                remoteKeysFeedDao.addAllRemoteKeys(remoteKeys = keys)

                userFeedDao.addUsers(users = users)

                val photos = response.map { it.toPhotoFeedEntity() }

                photoFeedDao.addPhotos(photos)

                reactionsFeedDao.addReactionsTypes(reactions = reactions)
                photoReactionsFeedDao.addPhotoReactions(photoReactions = photoReactions)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPagingReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoFeedJoinedDto>
    ): RemoteKeysFeedDto? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.photoId?.let { id ->
                remoteKeysFeedDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PhotoFeedJoinedDto>
    ): RemoteKeysFeedDto? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { photo ->
                remoteKeysFeedDao.getRemoteKeys(id = photo.photoId)
            }
    }

    private suspend fun getRemoteKetForLastItem(
        state: PagingState<Int, PhotoFeedJoinedDto>
    ): RemoteKeysFeedDto? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { photo ->
                remoteKeysFeedDao.getRemoteKeys(id = photo.photoId)
            }
    }

}