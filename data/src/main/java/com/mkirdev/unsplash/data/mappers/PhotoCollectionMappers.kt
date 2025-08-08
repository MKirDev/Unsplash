package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.list.PhotoCollectionNetwork
import com.mkirdev.unsplash.data.storages.database.dto.collection.PhotoCollectionJoinedDto
import com.mkirdev.unsplash.data.storages.database.entities.collection.RemoteKeysCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.PhotoCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.PhotoReactionsCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.ReactionsCollectionEntity
import com.mkirdev.unsplash.domain.models.Links
import com.mkirdev.unsplash.domain.models.Photo

internal fun PhotoCollectionNetwork.toKeysCollectionEntity(prevPage: Int?, nextPage: Int?) : RemoteKeysCollectionEntity {
    return RemoteKeysCollectionEntity(
        photoId = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}

internal fun PhotoCollectionNetwork.toReactionsCollectionEntity(): ReactionsCollectionEntity {
    return ReactionsCollectionEntity(
        photoId = id,
        liked = if (likedByUser) 1 else 0
    )
}

internal fun PhotoCollectionNetwork.toPhotoReactionsCollectionEntity(): PhotoReactionsCollectionEntity {
    return PhotoReactionsCollectionEntity(
        photoId = id,
        reactionsId = id
    )
}

internal fun PhotoCollectionNetwork.toPhotoCollectionEntity(collectionId: String): PhotoCollectionEntity {
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

    return PhotoCollectionEntity(
        photoId = id,
        width = displayWidth,
        height = displayHeight,
        imageUrl = imageUrl.toSmall(),
        downloadLink = links.toDownload(),
        htmlLink = links.toHtml(),
        likes = likes,
        userId = user.id,
        collectionId = collectionId
    )
}

internal fun PhotoCollectionJoinedDto.toDomain(): Photo {
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

internal fun PhotoCollectionNetwork.toDomain() =
    Photo(
        id = id,
        width = width,
        height = height,
        imageUrl = imageUrl.toSmall(),
        links = links.toDomain(),
        likes = likes,
        likedByUser = likedByUser,
        user = user.toDomain(),
        location = null,
        exif = null,
        tags = null,
        downloads = 0,
        position = 0
    )





