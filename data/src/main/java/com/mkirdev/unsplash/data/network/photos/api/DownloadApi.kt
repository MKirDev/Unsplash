package com.mkirdev.unsplash.data.network.photos.api

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadApi {
    @GET
    @Streaming
    fun download(@Url url: String): Single<ResponseBody>
}