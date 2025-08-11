package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.list.PhotoLikedNetwork
import com.mkirdev.unsplash.data.storages.database.dto.feed.PhotoFeedJoinedDto
import com.mkirdev.unsplash.data.storages.database.dto.liked.PhotoLikedJoinedDto
import com.mkirdev.unsplash.data.storages.database.dto.liked.UserLikedDto
import com.mkirdev.unsplash.data.storages.database.entities.liked.PhotoLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.PhotoReactionsLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.ReactionsLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.RemoteKeysLikedEntity
import com.mkirdev.unsplash.data.storages.database.view.PhotoLikedJoinedView
import com.mkirdev.unsplash.domain.models.Links
import com.mkirdev.unsplash.domain.models.Photo


internal fun PhotoLikedNetwork.toKeysLikedEntity(prevPage: Int?, nextPage: Int?) : RemoteKeysLikedEntity {
    return RemoteKeysLikedEntity(
        photoId = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}

internal fun PhotoLikedNetwork.toPhotoLikedEntity(): PhotoLikedEntity {
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

    return PhotoLikedEntity(
        photoId = id,
        width = displayWidth,
        height = displayHeight,
        imageUrl = imageUrl.toSmall(),
        downloadLink = links.toDownload(),
        htmlLink = links.toHtml(),
        likes = likes,
        userId = user.id
    )
}

internal fun PhotoLikedNetwork.toReactionsLikedEntity(): ReactionsLikedEntity {
    return ReactionsLikedEntity(
        photoId = id,
        liked = if (likedByUser) 1 else 0
    )
}

internal fun PhotoLikedNetwork.toPhotoReactionsLikedEntity(): PhotoReactionsLikedEntity {
    return PhotoReactionsLikedEntity(
        photoId = id,
        reactionsId = id
    )
}

internal fun PhotoLikedJoinedDto.toDomain(): Photo {
    return Photo(
        position = position,
        id = photoId,
        width = width,
        height = height,
        imageUrl = imageUrl,
        links = Links(html = htmlLink, download = downloadLink),
        likes = likes,
        likedByUser = likedByUser == 1,
        user = userLikedDto.toDomain(),
        location = null,
        exif = null,
        tags = null,
        downloads = null,
    )
}

fun PhotoLikedJoinedView.toDto(): PhotoLikedJoinedDto {
    return PhotoLikedJoinedDto(
        position = position,
        photoId = photoId,
        width = width,
        height = height,
        imageUrl = imageUrl,
        downloadLink = downloadLink,
        htmlLink = htmlLink,
        likes = likes,
        likedByUser = likedByUser,
        userLikedDto = UserLikedDto(
            id = userId,
            fullName = this.fullName,
            username = this.username,
            imageUrl = this.userImageUrl,
            bio = this.bio,
            location = this.location
        )
    )
}