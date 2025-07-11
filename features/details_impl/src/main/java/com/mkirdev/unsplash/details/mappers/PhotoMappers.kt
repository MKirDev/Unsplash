package com.mkirdev.unsplash.details.mappers

import androidx.compose.ui.unit.dp
import com.mkirdev.unsplash.details.models.DetailsModel
import com.mkirdev.unsplash.domain.models.Photo
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel

private const val EMPTY_STRING = ""

internal fun Photo.toPhotoItemModel(): PhotoItemModel = PhotoItemModel(
    id = id,
    imageUrl = imageUrl,
    width = width.dp,
    height = height.dp,
    aspectRatioImage = (width * 1f / height * 1f),
    user = user.toUserModel(),
    downloadLink = links.download,
    downloads = downloads?.toString() ?: EMPTY_STRING,
    likes = likes.toString(),
    isLiked = likedByUser
)

internal fun Photo.toDetailsModel(photoItemModel: PhotoItemModel): DetailsModel = DetailsModel(
    photoItemModel = photoItemModel,
    shareLink = links.toHtml(),
    location = location?.toLocationModel(),
    tags = tags?.let { it.map { tag ->
        tag?.trim()?.replace(" ", "")
    }.joinToString(separator = ", ", transform = { "#${it}" }) },
    exif = exif?.toExifModel(),
    bio = user.bio
)