package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.details.PhotoNetwork
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity
import com.mkirdev.unsplash.domain.models.Photo


internal fun PhotoNetwork.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        id = id,
        width = width,
        height = height,
        imageUrl = imageUrl.toRegular(),
        downloadLink = downloadLink.toDownload(),
        likes = likes,
        userId = user.id
    )
}

internal fun PhotoNetwork.toReactionTypeEntity(): ReactionsTypeEntity {
    return ReactionsTypeEntity(
        id = id,
        liked = if (likedByUser) 1 else 0
    )
}

internal fun PhotoNetwork.toPhotoReactionsEntity(): PhotoReactionsEntity {
    return PhotoReactionsEntity(
        photoId = id,
        reactionsId = id
    )
}

internal fun PhotoNetwork.toDomain(): Photo {
    return Photo(
        id = id,
        width = width,
        height = height,
        imageUrl = imageUrl.toRegular(),
        downloadLink = downloadLink.toDownload(),
        likes = likes,
        likedByUser = likedByUser,
        user = user.toDomain(),
        location = location.toDomain(),
        exif = exif.toDomain(),
        tags = tags?.map { it.toTitle() } ?: emptyList(),
        downloads = downloads
    )
}