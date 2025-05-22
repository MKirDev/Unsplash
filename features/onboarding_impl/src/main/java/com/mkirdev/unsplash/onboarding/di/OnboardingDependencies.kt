package com.mkirdev.unsplash.onboarding.di

import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApi
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApi

interface OnboardingDependencies {
    val contentCreationFeatureApi: ContentCreationFeatureApi
    val socialCollectionsFeatureApi: SocialCollectionsFeatureApi
    val uploadAndTrackFeatureApi: UploadAndTrackFeatureApi
}