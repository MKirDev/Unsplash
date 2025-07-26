package com.mkirdev.unsplash.data.network.models.collections

import com.google.gson.annotations.SerializedName
import com.mkirdev.unsplash.data.network.models.common.UrlNetwork

data class CoverPhotoNetwork(
    @SerializedName("id")
    val id: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("urls")
    val imageUrl: UrlNetwork
)