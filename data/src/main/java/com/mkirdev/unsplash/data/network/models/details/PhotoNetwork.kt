package com.mkirdev.unsplash.data.network.models.details

import com.google.gson.annotations.SerializedName
import com.mkirdev.unsplash.data.network.models.common.LinksNetwork
import com.mkirdev.unsplash.data.network.models.common.UrlNetwork
import com.mkirdev.unsplash.data.network.models.common.UserNetwork

data class PhotoNetwork(
    @SerializedName("id")
    val id: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("urls")
    val imageUrl: UrlNetwork,
    @SerializedName("links")
    val links: LinksNetwork,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("user")
    val user: UserNetwork,
    @SerializedName("exif")
    val exif: ExifNetwork?,
    @SerializedName("location")
    val location: LocationNetwork?,
    @SerializedName("tags")
    val tags: List<TagNetwork>?,
    @SerializedName("downloads")
    val downloads: Int?
)
