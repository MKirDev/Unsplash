package com.mkirdev.unsplash.upload_and_track.api

import androidx.compose.runtime.Composable
import javax.inject.Inject

class UploadAndTrackFeatureApiImpl @Inject constructor() : UploadAndTrackFeatureApi {
    @Composable
    override fun UploadAndTrackScreen(text: String): Unit =
        UploadAndTrackScreen(text = text)
}