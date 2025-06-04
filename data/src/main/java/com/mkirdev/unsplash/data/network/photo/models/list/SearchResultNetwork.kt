package com.mkirdev.unsplash.data.network.photo.models.list

import com.google.gson.annotations.SerializedName

data class SearchResultNetwork(
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val results: List<PhotoFeedNetwork>
)