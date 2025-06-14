package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.details.PhotoDetailsNetwork
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity


internal fun PhotoDetailsNetwork.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        id = id,
        width = width,
        height = height,
        imageUrl = imageUrl.toRegular(),
        downloadLink = downloadLink.toDownload(),
        likes = likes,
        userId = user.id,
        searchType = 1
    )
}

internal fun PhotoDetailsNetwork.toReactionTypeEntity(): ReactionsTypeEntity {
    return ReactionsTypeEntity(
        id = id,
        liked = if (likedByUser) 1 else 0
    )
}

internal fun PhotoDetailsNetwork.toPhotoReactionsEntity(): PhotoReactionsEntity {
    return PhotoReactionsEntity(
        photoId = id,
        reactionsId = id
    )
}