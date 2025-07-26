package com.mkirdev.unsplash.data.network.models.collections

import com.google.gson.annotations.SerializedName
import com.mkirdev.unsplash.data.network.models.common.UrlNetwork

data class PreviewPhotoNetwork(
    @SerializedName("id")
    val id: String,
    @SerializedName("urls")
    val imageUrl: UrlNetwork
)