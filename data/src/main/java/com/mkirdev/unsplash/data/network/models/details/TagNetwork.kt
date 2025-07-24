package com.mkirdev.unsplash.data.network.models.details

import com.google.gson.annotations.SerializedName

data class TagNetwork(
    @SerializedName("type")
    val type: String?,
    @SerializedName("title")
    val title: String?
)
