package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.details.TagNetwork

private const val EMPTY_STRING = ""
internal fun TagNetwork.toTitle(): String {
    return title ?: EMPTY_STRING
}