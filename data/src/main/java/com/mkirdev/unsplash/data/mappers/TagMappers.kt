package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.details.TagNetwork

internal fun TagNetwork.toTitle(): String? {
    return if (title.isNullOrEmpty()) null else title
}