package com.mkirdev.unsplash.onboarding.di

import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import com.mkirdev.unsplash.notification.api.NotificationFeatureApi
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApi
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApi

interface OnboardingDependencies {
    val onboardingRepository: OnboardingRepository
    val contentCreationFeatureApi: ContentCreationFeatureApi
    val socialCollectionsFeatureApi: SocialCollectionsFeatureApi
    val uploadAndTrackFeatureApi: UploadAndTrackFeatureApi

    val notificationFeatureApi: NotificationFeatureApi
}