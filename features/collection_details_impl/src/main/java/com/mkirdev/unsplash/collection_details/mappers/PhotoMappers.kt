package com.mkirdev.unsplash.collection_details.mappers

import androidx.compose.ui.unit.dp
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel

private const val EMPTY_STRING = ""

internal fun Photo.toPresentation() = PhotoItemModel(
    id = id,
    imageUrl = imageUrl,
    width = width.dp,
    height = height.dp,
    aspectRatioImage = (width.toFloat() / height.toFloat()),
    user = user.toPresentation(),
    downloadLink = links.toDownload(),
    downloads = downloads?.toString() ?: EMPTY_STRING,
    likes = likes.toString(),
    isLiked = likedByUser
)