package com.mkirdev.unsplash.data.network.models.collections

import com.google.gson.annotations.SerializedName
import com.mkirdev.unsplash.data.network.models.common.UserNetwork

data class CollectionNetwork(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("user")
    val user: UserNetwork,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhotoNetwork?,
    @SerializedName("preview_photos")
    val previewPhotos: List<PreviewPhotoNetwork>?
)