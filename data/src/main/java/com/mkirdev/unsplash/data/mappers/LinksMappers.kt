package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.common.LinksNetwork

internal fun LinksNetwork.toDownload(): String {
    return download
}