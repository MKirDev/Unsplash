package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.list.PhotoSearchNetwork
import com.mkirdev.unsplash.data.storages.database.dto.search.PhotoSearchJoinedDto
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.PhotoReactionsSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.PhotoSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.ReactionsSearchEntity
import com.mkirdev.unsplash.domain.models.Links
import com.mkirdev.unsplash.domain.models.Photo
internal fun PhotoSearchNetwork.toKeysSearchEntity(prevPage: Int?, nextPage: Int?) : RemoteKeysSearchEntity {
    return RemoteKeysSearchEntity(
        photoId = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}

internal fun PhotoSearchNetwork.toPhotoSearchEntity(): PhotoSearchEntity {
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

    return PhotoSearchEntity(
        photoId = id,
        width = displayWidth,
        height = displayHeight,
        imageUrl = imageUrl.toRegular(),
        downloadLink = links.toDownload(),
        htmlLink = links.toHtml(),
        likes = likes,
        userId = user.id,
    )
}

internal fun PhotoSearchNetwork.toReactionsSearchEntity(): ReactionsSearchEntity {
    return ReactionsSearchEntity(
        photoId = id,
        liked = if (likedByUser) 1 else 0
    )
}

internal fun PhotoSearchNetwork.toPhotoReactionsSearchEntity(): PhotoReactionsSearchEntity {
    return PhotoReactionsSearchEntity(
        photoId = id,
        reactionsId = id
    )
}

internal fun PhotoSearchJoinedDto.toDomain(): Photo {
    return Photo(
        position = position,
        id = photoId,
        width = width,
        height = height,
        imageUrl = imageUrl,
        links = Links(html = htmlLink, download = downloadLink),
        likes = likes,
        likedByUser = likedByUser == 1,
        user = userSearchDto.toDomain(),
        location = null,
        exif = null,
        tags = null,
        downloads = null,
    )
}
