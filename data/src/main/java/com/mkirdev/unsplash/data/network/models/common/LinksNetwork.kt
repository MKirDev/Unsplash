package com.mkirdev.unsplash.data.network.models.common

import com.google.gson.annotations.SerializedName

data class LinksNetwork(
    @SerializedName("html")
    val html: String,
    @SerializedName("download")
    val download: String
)