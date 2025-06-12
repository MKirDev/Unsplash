package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.list.PhotoFeedNetwork
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedDto
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysEntity
import com.mkirdev.unsplash.domain.models.Photo

internal fun PhotoFeedNetwork.toKeysEntity(prevPage: Int?, nextPage: Int?) : RemoteKeysEntity {
    return RemoteKeysEntity(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}

internal fun PhotoFeedNetwork.toPhotoEntity(): PhotoEntity {
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

internal fun PhotoFeedNetwork.toReactionsTypeEntity(): ReactionsTypeEntity {
    return ReactionsTypeEntity(
        id = id,
        liked = if (likedByUser) 1 else 0
    )
}

internal fun PhotoFeedNetwork.toPhotoReactionsEntity(): PhotoReactionsEntity {
    return PhotoReactionsEntity(
        photoId = id,
        reactionsId = id
    )
}

internal fun PhotoFeedDto.toDomain(): Photo {
    return Photo(
        id = id,
        width = width,
        height = height,
        imageUrl = imageUrl,
        downloadLink = downloadLink,
        likes = likes,
        likedByUser = likedByUser == 1,
        user = userDto.toDomain(),
        location = null,
        exif = null,
        tags = null,
        downloads = null
    )
}