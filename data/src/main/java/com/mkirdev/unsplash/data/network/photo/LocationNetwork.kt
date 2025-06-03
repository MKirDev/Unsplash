package com.mkirdev.unsplash.data.network.photo

import com.google.gson.annotations.SerializedName

data class LocationNetwork(
    @SerializedName("name")
    val name: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("position")
    val coordinates: CoordinatesNetwork
)
