package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.common.LinksNetwork
import com.mkirdev.unsplash.domain.models.Links

internal fun LinksNetwork.toDownload(): String {
    return download
}

internal fun LinksNetwork.toHtml(): String {
    return html
}

internal fun LinksNetwork.toDomain(): Links {
    return Links(
        html = html,
        download = download
    )
}