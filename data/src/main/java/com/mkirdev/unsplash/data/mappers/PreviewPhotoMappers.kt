package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.collections.PreviewPhotoNetwork

internal fun PreviewPhotoNetwork.toPreviewPhotoUrl() = imageUrl.toRegular()