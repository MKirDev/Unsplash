package com.mkirdev.unsplash.upload_and_track.api

import androidx.compose.runtime.Composable
import com.mkirdev.unsplash.upload_and_track.impl.UploadAndTrackScreen

class UploadAndTrackFeatureApiImpl : UploadAndTrackFeatureApi {
    @Composable
    override fun UploadAndTrackScreen(text: String): Unit =
        UploadAndTrackScreen(text = text)
}