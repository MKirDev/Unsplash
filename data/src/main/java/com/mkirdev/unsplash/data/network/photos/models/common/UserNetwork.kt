package com.mkirdev.unsplash.data.network.photos.models.common

import com.google.gson.annotations.SerializedName

data class UserNetwork(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("profile_image")
    val imageUrl: ProfileImageNetwork
)
