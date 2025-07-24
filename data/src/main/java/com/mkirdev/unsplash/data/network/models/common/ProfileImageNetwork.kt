package com.mkirdev.unsplash.data.network.models.common

import com.google.gson.annotations.SerializedName

data class ProfileImageNetwork(
    @SerializedName("small")
    val small: String
)
