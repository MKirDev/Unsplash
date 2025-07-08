package com.mkirdev.unsplash.data.network.photos.models.details

import com.google.gson.annotations.SerializedName

data class CoordinatesNetwork(
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?
)