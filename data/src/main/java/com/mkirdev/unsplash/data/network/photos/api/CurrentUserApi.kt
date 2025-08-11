package com.mkirdev.unsplash.data.network.photos.api

import com.mkirdev.unsplash.data.BuildConfig
import com.mkirdev.unsplash.data.network.models.common.CurrentUserNetwork
import com.mkirdev.unsplash.data.network.models.list.PhotoLikedNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrentUserApi {
    @GET("${BuildConfig.FIRST_PATH_LIKED_PHOTOS}{username}${BuildConfig.SECOND_PATH_LIKED_PHOTOS}")
    suspend fun getLikedPhotos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<PhotoLikedNetwork>

    @GET(BuildConfig.PATH_USER_INFO)
    suspend fun getCurrentUser(): CurrentUserNetwork
}