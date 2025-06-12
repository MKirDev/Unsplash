package com.mkirdev.unsplash.data.network.photos.api

import com.mkirdev.unsplash.data.BuildConfig
import com.mkirdev.unsplash.data.network.photos.models.list.SearchResultNetwork
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET(BuildConfig.PATH_SEARCH_PHOTOS)
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : SearchResultNetwork

}