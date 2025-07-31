package com.mkirdev.unsplash.data.network.collections.api


import com.mkirdev.unsplash.data.BuildConfig
import com.mkirdev.unsplash.data.network.models.collections.CollectionNetwork
import com.mkirdev.unsplash.data.network.models.list.PhotoCollectionNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionsApi {
    @GET(BuildConfig.PATH_LIST_COLLECTIONS)
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<CollectionNetwork>

    @GET("${BuildConfig.PATH_COLLECTION}{id}")
    suspend fun getCollection(
        @Path("id") id: String
    ): CollectionNetwork

    @GET("${BuildConfig.PATH_COLLECTION}{id}/${BuildConfig.PATH_COLLECTION_PHOTOS}")
    suspend fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<PhotoCollectionNetwork>

}