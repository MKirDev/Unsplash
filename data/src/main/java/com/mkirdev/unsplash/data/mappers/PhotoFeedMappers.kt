package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photo.models.list.PhotoFeedNetwork
import com.mkirdev.unsplash.data.storages.database.dto.base.PhotoDto
import com.mkirdev.unsplash.data.storages.database.dto.base.PhotoReactionsDto
import com.mkirdev.unsplash.data.storages.database.dto.base.ReactionsTypeDto
import com.mkirdev.unsplash.data.storages.database.dto.base.RemoteKeysDto

internal fun PhotoFeedNetwork.toKeysDto(prevPage: Int?, nextPage: Int?) : RemoteKeysDto {
    return RemoteKeysDto(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}

internal fun PhotoFeedNetwork.toPhotoDto(): PhotoDto {
    return PhotoDto(
        id = id,
        width = width,
        height = height,
        imageUrl = imageUrl.toRegular(),
        downloadLink = downloadLink.toDownload(),
        likedByUser = if (likedByUser) 1 else 0,
        likes = likes,
        userId = user.id
    )
}

internal fun PhotoFeedNetwork.toReactionsTypeDto(): ReactionsTypeDto {
    return ReactionsTypeDto(
        id = id,
        liked = if (likedByUser) 1 else 0
    )
}

internal fun PhotoFeedNetwork.toPhotoReactionsDto(): PhotoReactionsDto {
    return PhotoReactionsDto(
        photoId = id,
        reactionsId = id
    )
}