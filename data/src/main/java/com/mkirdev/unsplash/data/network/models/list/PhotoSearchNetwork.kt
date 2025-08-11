package com.mkirdev.unsplash.data.network.models.list

import com.google.gson.annotations.SerializedName
import com.mkirdev.unsplash.data.network.models.common.LinksNetwork
import com.mkirdev.unsplash.data.network.models.common.UrlNetwork
import com.mkirdev.unsplash.data.network.models.common.UserNetwork
import com.mkirdev.unsplash.data.network.models.list.interfaces.ListNetwork

data class PhotoSearchNetwork(
    @SerializedName("id")
    override val id: String,
    @SerializedName("width")
    override val width: Int,
    @SerializedName("height")
    override val height: Int,
    @SerializedName("urls")
    override val imageUrl: UrlNetwork,
    @SerializedName("links")
    override val links: LinksNetwork,
    @SerializedName("likes")
    override val likes: Int,
    @SerializedName("liked_by_user")
    override val likedByUser: Boolean,
    @SerializedName("user")
    override val user: UserNetwork
) : ListNetwork