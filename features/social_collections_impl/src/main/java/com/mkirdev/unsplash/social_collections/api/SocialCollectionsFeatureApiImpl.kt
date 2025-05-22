package com.mkirdev.unsplash.social_collections.api

import androidx.compose.runtime.Composable
import com.mkirdev.unsplash.social_collections.impl.SocialCollectionsScreen
import javax.inject.Inject

class SocialCollectionsFeatureApiImpl @Inject constructor() : SocialCollectionsFeatureApi {
    @Composable
    override fun SocialCollectionsFeature(text: String): Unit = SocialCollectionsScreen(text = text)
}