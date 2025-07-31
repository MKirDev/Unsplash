package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.collections.CollectionNetwork
import com.mkirdev.unsplash.data.storages.database.entities.CollectionEntity
import com.mkirdev.unsplash.domain.models.Collection

internal fun CollectionNetwork.toDomain() = Collection(
    title = title,
    description = description,
    totalPhotos = totalPhotos,
    user = user.toDomain(),
    coverPhotoUrl = coverPhoto?.toCoverPhotoUrl() ?: throw Throwable(),
    previewPhotos?.first()?.toPreviewPhotoUrl() ?: throw Throwable(),
)

internal fun CollectionNetwork.toCollectionEntity() = CollectionEntity(
    id = id,
    name = title
)