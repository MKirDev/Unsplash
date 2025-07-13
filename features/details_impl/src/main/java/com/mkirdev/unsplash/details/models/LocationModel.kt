package com.mkirdev.unsplash.details.models

import androidx.compose.runtime.Immutable

@Immutable
data class LocationModel(
    val place: String?,
    val coordinatesModel: CoordinatesModel?
)