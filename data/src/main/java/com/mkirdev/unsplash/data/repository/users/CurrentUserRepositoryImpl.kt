package com.mkirdev.unsplash.data.repository.users

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mkirdev.unsplash.data.exceptions.UsersException
import com.mkirdev.unsplash.data.mappers.toDomain
import com.mkirdev.unsplash.data.network.models.list.PhotoFeedNetwork
import com.mkirdev.unsplash.data.network.photos.api.CurrentUserApi
import com.mkirdev.unsplash.data.paging.UserPhotosPagingSource
import com.mkirdev.unsplash.domain.models.CurrentUser
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.domain.models.User
import com.mkirdev.unsplash.domain.repository.CurrentUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

class CurrentUserRepositoryImpl @Inject constructor(
    private val currentUserApi: CurrentUserApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CurrentUserRepository {
    override fun getLikedPhotos(username: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                UserPhotosPagingSource(
                    username = username,
                    currentUserApi = currentUserApi
                )
            }
        ).flow.map { value: PagingData<PhotoFeedNetwork> ->
            value.map { photoFeedNetwork -> photoFeedNetwork.toDomain() }
        }.flowOn(dispatcher).catch {
            throw UsersException.GetLikedPhotosException(it)
        }
    }

    override suspend fun getUserInfo(): CurrentUser = withContext(dispatcher) {
        try {
            currentUserApi.getUserInfo().toDomain()
        } catch (t: Throwable) {
            throw UsersException.GetUserInfoException(t)
        }
    }
}