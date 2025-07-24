package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.common.UrlNetwork

internal fun UrlNetwork.toRegular(): String {
    return regular
}