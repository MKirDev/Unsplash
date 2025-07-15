package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.details.PhotoNetwork
import com.mkirdev.unsplash.domain.models.Photo

internal fun PhotoNetwork.toDomain(): Photo {
    return Photo(
        id = id,
        width = width,
        height = height,
        imageUrl = imageUrl.toRegular(),
        links = links.toDomain(),
        likes = likes,
        likedByUser = likedByUser,
        user = user.toDomain(),
        location = location?.toDomain(),
        exif = exif?.toDomain(),
        tags = if (tags.isNullOrEmpty()) null else tags.map { it.toTitle() },
        downloads = downloads,
        position = 0
    )
}