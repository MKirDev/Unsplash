package com.mkirdev.unsplash.social_collections.api

import androidx.compose.runtime.Composable
import javax.inject.Inject

class SocialCollectionsFeatureApiImpl @Inject constructor() : SocialCollectionsFeatureApi {
    @Composable
    override fun SocialCollectionsScreen(text: String): Unit = SocialCollectionsScreen(text = text)
}