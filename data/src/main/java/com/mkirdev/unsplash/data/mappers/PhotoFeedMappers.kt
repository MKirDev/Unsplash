package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.list.PhotoFeedNetwork
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedDto
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysFeedEntity
import com.mkirdev.unsplash.domain.models.Links
import com.mkirdev.unsplash.domain.models.Photo

internal fun PhotoFeedNetwork.toKeysEntity(prevPage: Int?, nextPage: Int?) : RemoteKeysFeedEntity {
    return RemoteKeysFeedEntity(
        photoId = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}

internal fun PhotoFeedNetwork.toPhotoEntity(position: Int): PhotoEntity {
    val displayWidth = when (width) {
        in 0..2500 -> 180
        in 2501..4000 -> 240
        else -> 300
    }
    val displayHeight = when (height) {
        in 0..2500 -> 150
        in 2501..4000 -> 250
        else -> 350
    }

    return PhotoEntity(
        id = id,
        width = displayWidth,
        height = displayHeight,
        imageUrl = imageUrl.toRegular(),
        downloadLink = links.toDownload(),
        htmlLink = links.toHtml(),
        likes = likes,
        userId = user.id,
        searchType = 0,
        position = position
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
        links = Links(html = htmlLink, download = downloadLink),
        likes = likes,
        likedByUser = likedByUser == 1,
        user = userDto.toDomain(),
        location = null,
        exif = null,
        tags = null,
        downloads = null,
        position = position
    )
}