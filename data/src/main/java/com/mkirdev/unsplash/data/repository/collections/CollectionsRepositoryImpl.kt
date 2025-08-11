package com.mkirdev.unsplash.data.repository.collections

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mkirdev.unsplash.data.exceptions.CollectionsException
import com.mkirdev.unsplash.data.mappers.toDomain
import com.mkirdev.unsplash.data.network.collections.api.CollectionsApi
import com.mkirdev.unsplash.data.network.models.collections.CollectionNetwork
import com.mkirdev.unsplash.data.paging.CollectionPhotosRemoteMediator
import com.mkirdev.unsplash.data.paging.CollectionsPagingSource
import com.mkirdev.unsplash.data.storages.database.dto.collection.PhotoCollectionJoinedDto
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import com.mkirdev.unsplash.domain.models.Collection
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.CollectionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 10

@OptIn(ExperimentalPagingApi::class)
class CollectionsRepositoryImpl @Inject constructor(
    private val collectionsApi: CollectionsApi,
    private val appDatabase: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CollectionsRepository {
    override fun getCollections(): Flow<PagingData<Collection>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                CollectionsPagingSource(collectionsApi = collectionsApi)
            }
        ).flow.map { value: PagingData<CollectionNetwork> ->
            value.map { collectionNetwork -> collectionNetwork.toDomain() }
        }.flowOn(dispatcher).catch {
            throw CollectionsException.GetCollectionsException(it)
        }
    }

    override suspend fun getCollectionInfo(id: String): Collection = withContext(dispatcher) {
        try {
            val collectionNetwork = collectionsApi.getCollection(id)
            collectionNetwork.toDomain()
        } catch (t: Throwable) {
            throw CollectionsException.GetCollectionInfoException(t)
        }
    }

    override fun getCollectionPhotos(id: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = CollectionPhotosRemoteMediator(
                collectionId = id,
                collectionsApi = collectionsApi,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = {
                appDatabase.photoCollectionJoinedDao().getCollectionJoinedPhotos()
            }
        ).flow.map { value: PagingData<PhotoCollectionJoinedDto> ->
            value.map { photoCollectionJoinedDto -> photoCollectionJoinedDto.toDomain() }
        }.flowOn(dispatcher).catch {
            throw CollectionsException.GetCollectionPhotosException(it)
        }
    }

    override suspend fun clearCollectionsFromDatabase(): Unit = withContext(dispatcher) {
        try {
            appDatabase.photoCollectionDao().deletePhotos()
            appDatabase.userCollectionDao().deleteUsers()
            appDatabase.reactionsCollectionDao().deleteReactionsTypes()
            appDatabase.remoteKeysCollectionDao().deleteAllRemoteKeys()
        } catch (t: Throwable) {
            throw CollectionsException.ClearCollectionsFromDatabase(t)
        }
    }
}