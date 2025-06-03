package com.mkirdev.unsplash.data.network.photo

import com.google.gson.annotations.SerializedName

data class UrlNetwork(
    @SerializedName("regular")
    val regular: String
)
