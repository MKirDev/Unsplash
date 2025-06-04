package com.mkirdev.unsplash.data.network.photo.models.common

import com.google.gson.annotations.SerializedName

data class LinksNetwork(
    @SerializedName("download")
    val download: String
)