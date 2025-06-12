package com.mkirdev.unsplash.data.network.photos.models.details

import com.google.gson.annotations.SerializedName

data class ExifNetwork(
    @SerializedName("make")
    val make: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("exposure_time")
    val exposureTime: String?,
    @SerializedName("aperture")
    val aperture: String?,
    @SerializedName("focal_length")
    val focalLength: String?,
    @SerializedName("iso")
    val iso: String?
)
