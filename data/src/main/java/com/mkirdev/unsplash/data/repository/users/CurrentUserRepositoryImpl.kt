package com.mkirdev.unsplash.data.repository.users

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mkirdev.unsplash.data.exceptions.UserException
import com.mkirdev.unsplash.data.mappers.toDomain
import com.mkirdev.unsplash.data.mappers.toDto
import com.mkirdev.unsplash.data.mappers.toUserProfileEntity
import com.mkirdev.unsplash.data.network.photos.api.CurrentUserApi
import com.mkirdev.unsplash.data.paging.PhotoLikedRemoteMediator
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import com.mkirdev.unsplash.data.storages.database.view.PhotoLikedJoinedView
import com.mkirdev.unsplash.domain.models.CurrentUser
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.repository.CurrentUserRepository
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
class CurrentUserRepositoryImpl @Inject constructor(
    private val currentUserApi: CurrentUserApi,
    private val appDatabase: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CurrentUserRepository {
    override fun getLikedPhotos(username: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PhotoLikedRemoteMediator(
                username = username,
                currentUserApi = currentUserApi,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = {
                appDatabase.photoLikedJoinedDao().getLikedJoinedPhotos()
            }
        ).flow.map { value: PagingData<PhotoLikedJoinedView> ->
            value.map { photoLikedJoinedView -> photoLikedJoinedView.toDto().toDomain() }
        }.flowOn(dispatcher).catch {
            throw UserException.GetLikedPhotosException(it)
        }
    }

    override suspend fun getCurrentUser(): CurrentUser = withContext(dispatcher) {
        try {
            appDatabase.userProfileDao().getUser()?.toDomain() ?: throw Throwable()
        } catch (t: Throwable) {
            throw UserException.GetCurrentUserException(t)
        }
    }

    override suspend fun addCurrentUser() = withContext(dispatcher) {
        try {
            val user = currentUserApi.getCurrentUser().toUserProfileEntity()
            appDatabase.userProfileDao().addUser(user)
        } catch (t: Throwable) {
            throw UserException.AddCurrentUserException(t)
        }
    }

    override suspend fun clearUserFromDatabase() = withContext(dispatcher) {
        try {
            appDatabase.userProfileDao().deleteUser()
        } catch (t: Throwable) {
            throw UserException.ClearUserFromDatabase(t)
        }
    }
}