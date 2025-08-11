package com.mkirdev.unsplash.data.network.models.common

import com.google.gson.annotations.SerializedName

data class CurrentUserNetwork(
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
    @SerializedName("profile_image")
    val imageUrl: ProfileImageNetwork,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("email")
    val email: String?
)
