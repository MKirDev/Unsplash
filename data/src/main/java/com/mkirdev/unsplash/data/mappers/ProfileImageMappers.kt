package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.common.ProfileImageNetwork

internal fun ProfileImageNetwork.toSmall(): String {
    return small
}