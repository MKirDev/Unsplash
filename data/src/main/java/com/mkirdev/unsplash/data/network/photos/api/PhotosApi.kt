package com.mkirdev.unsplash.data.network.photos.api

import com.mkirdev.unsplash.data.BuildConfig
import com.mkirdev.unsplash.data.network.models.details.PhotoNetwork
import com.mkirdev.unsplash.data.network.models.list.PhotoFeedNetwork
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotosApi {
    @GET(BuildConfig.PATH_LIST_PHOTOS)
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : List<PhotoFeedNetwork>

    @GET("${BuildConfig.PATH_PHOTO}{id}")
    suspend fun getPhoto(
        @Path("id") id: String
    ) : PhotoNetwork

    @POST("${BuildConfig.FIRST_PATH_LIKE_PHOTO}{id}${BuildConfig.SECOND_PATH_LIKE_PHOTO}")
    suspend fun likePhoto(
        @Path("id") id: String
    ) : PhotoFeedNetwork

    @DELETE("${BuildConfig.FIRST_PATH_UNLIKE_PHOTO}{id}${BuildConfig.SECOND_PATH_UNLIKE_PHOTO}")
    suspend fun unLikePhoto(
        @Path("id") id: String
    ) : PhotoFeedNetwork
}