package com.mkirdev.unsplash.upload_and_track.api

import androidx.compose.runtime.Composable
import com.mkirdev.unsplash.upload_and_track.impl.UploadAndTrackScreen
import javax.inject.Inject

class UploadAndTrackFeatureApiImpl @Inject constructor() : UploadAndTrackFeatureApi {
    @Composable
    override fun UploadAndTrackFeature(text: String): Unit =
        UploadAndTrackScreen(text = text)
}