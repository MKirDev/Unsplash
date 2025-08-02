package com.mkirdev.unsplash.collections.mappers

import com.mkirdev.unsplash.collection_item.models.CollectionItemModel
import com.mkirdev.unsplash.domain.models.Collection

internal fun Collection.toPresentation() =
    CollectionItemModel(
        id = id,
        title = title,
        totalPhotos = totalPhotos.toString(),
        user = user.toPresentation(),
        coverPhotoUrl = coverPhotoUrl
    )