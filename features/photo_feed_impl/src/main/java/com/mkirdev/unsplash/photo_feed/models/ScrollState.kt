package com.mkirdev.unsplash.photo_feed.models

import androidx.compose.runtime.Immutable

@Immutable
data class ScrollState(
    val index: Int = 0,
    val offset: Int = 0
)
