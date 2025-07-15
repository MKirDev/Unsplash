package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.list.PhotoSearchNetwork
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysSearchEntity

internal fun PhotoSearchNetwork.toKeysEntity(prevPage: Int?, nextPage: Int?) : RemoteKeysSearchEntity {
    return RemoteKeysSearchEntity(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}

internal fun PhotoSearchNetwork.toPhotoEntity(position: Int): PhotoEntity {
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
        searchType = 1,
        position = position
    )
}

internal fun PhotoSearchNetwork.toReactionsTypeEntity(): ReactionsTypeEntity {
    return ReactionsTypeEntity(
        id = id,
        liked = if (likedByUser) 1 else 0
    )
}

internal fun PhotoSearchNetwork.toPhotoReactionsEntity(): PhotoReactionsEntity {
    return PhotoReactionsEntity(
        photoId = id,
        reactionsId = id
    )
}
