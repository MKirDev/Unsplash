package com.mkirdev.unsplash.details.models

import androidx.compose.runtime.Immutable

@Immutable
data class CoordinatesModel(
    val latitude: Double?,
    val longitude: Double?
)