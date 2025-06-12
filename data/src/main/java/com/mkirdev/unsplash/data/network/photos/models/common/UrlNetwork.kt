package com.mkirdev.unsplash.data.network.photos.models.common

import com.google.gson.annotations.SerializedName

data class UrlNetwork(
    @SerializedName("regular")
    val regular: String
)
