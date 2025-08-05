package com.mkirdev.unsplash.collection_details.mappers

import com.mkirdev.unsplash.collection_details.models.CollectionDetailsModel
import com.mkirdev.unsplash.domain.models.Collection

internal fun Collection.toPresentation() =
    CollectionDetailsModel(
        title = title,
        description = description,
        totalPhotos = totalPhotos.toString(),
        previewPhotoUrl = previewPhotoUrl,
        username = user.toUsername()
    )