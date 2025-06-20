package com.mkirdev.unsplash.photo_feed.mappers

import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel

private const val EMPTY_STRING = ""

internal fun Photo.toPresentation(): PhotoItemModel {
    return PhotoItemModel(
        id = id,
        imageUrl = imageUrl,
        width = width,
        height = height,
        aspectRatioImage = (width.toFloat() / height.toFloat()),
        user = user.toPresentation(),
        downloadLink = downloadLink,
        downloads = downloads?.toString() ?: EMPTY_STRING,
        likes = likes.toString(),
        isLiked = likedByUser
    )
}