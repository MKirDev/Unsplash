package com.mkirdev.unsplash.data.network.models.list.interfaces

import com.mkirdev.unsplash.data.network.models.common.LinksNetwork
import com.mkirdev.unsplash.data.network.models.common.UrlNetwork
import com.mkirdev.unsplash.data.network.models.common.UserNetwork

interface ListNetwork {
    val id: String
    val width: Int
    val height: Int
    val imageUrl: UrlNetwork
    val links: LinksNetwork
    val likes: Int
    val likedByUser: Boolean
    val user: UserNetwork
}