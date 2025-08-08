package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.common.UrlNetwork
import androidx.core.net.toUri

internal fun UrlNetwork.toRegular(): String {
    return regular
}

internal fun UrlNetwork.toSmall(): String {
    val uri = small.toUri()
    return uri.buildUpon()
        .clearQuery()
        .appendQueryParameter("q", "80")
        .appendQueryParameter("fm", "jpg")
        .appendQueryParameter("w", "720")
        .appendQueryParameter("fit", "max")
        .build()
        .toString()
}