package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.list.PhotoFeedNetwork
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedJoinedDto
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoReactionsFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.ReactionsFeedEntity
import com.mkirdev.unsplash.domain.models.Links
import com.mkirdev.unsplash.domain.models.Photo


internal fun PhotoFeedNetwork.toKeysFeedEntity(prevPage: Int?, nextPage: Int?) : RemoteKeysFeedEntity {
    return RemoteKeysFeedEntity(
        photoId = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}

internal fun PhotoFeedNetwork.toPhotoFeedEntity(): PhotoFeedEntity {
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

    return PhotoFeedEntity(
        photoId = id,
        width = displayWidth,
        height = displayHeight,
        imageUrl = imageUrl.toRegular(),
        downloadLink = links.toDownload(),
        htmlLink = links.toHtml(),
        likes = likes,
        userId = user.id
    )
}

internal fun PhotoFeedNetwork.toReactionsFeedEntity(): ReactionsFeedEntity {
    return ReactionsFeedEntity(
        photoId = id,
        liked = if (likedByUser) 1 else 0
    )
}

internal fun PhotoFeedNetwork.toPhotoReactionsFeedEntity(): PhotoReactionsFeedEntity {
    return PhotoReactionsFeedEntity(
        photoId = id,
        reactionsId = id
    )
}

internal fun PhotoFeedNetwork.toDomain(): Photo {
    return Photo(
        id = id,
        width = width,
        height = height,
        imageUrl = imageUrl.toRegular(),
        links = Links(html = links.toHtml(), download = links.toDownload()),
        likes = likes,
        likedByUser = likedByUser,
        user = user.toDomain(),
        location = null,
        exif = null,
        tags = null,
        downloads = null,
        position = 0
    )
}

internal fun PhotoFeedJoinedDto.toDomain(): Photo {
    return Photo(
        position = position,
        id = photoId,
        width = width,
        height = height,
        imageUrl = imageUrl,
        links = Links(html = htmlLink, download = downloadLink),
        likes = likes,
        likedByUser = likedByUser == 1,
        user = userFeedDto.toDomain(),
        location = null,
        exif = null,
        tags = null,
        downloads = null,
    )
}