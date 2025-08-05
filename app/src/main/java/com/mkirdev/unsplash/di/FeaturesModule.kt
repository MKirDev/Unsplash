package com.mkirdev.unsplash.di

import com.mkirdev.unsplash.auth.api.AuthFeatureApi
import com.mkirdev.unsplash.auth.api.AuthFeatureApiImpl
import com.mkirdev.unsplash.bottom_menu.api.BottomMenuFeatureApi
import com.mkirdev.unsplash.bottom_menu.api.BottomMenuFeatureApiImpl
import com.mkirdev.unsplash.collection_details.api.CollectionDetailsFeatureApi
import com.mkirdev.unsplash.collection_details.api.CollectionDetailsFeatureApiImpl
import com.mkirdev.unsplash.collections.api.CollectionsFeatureApi
import com.mkirdev.unsplash.collections.api.CollectionsFeatureApiImpl
import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApiImpl
import com.mkirdev.unsplash.details.api.DetailsFeatureApi
import com.mkirdev.unsplash.details.api.DetailsFeatureApiImpl
import com.mkirdev.unsplash.notification.api.NotificationFeatureApi
import com.mkirdev.unsplash.notification.api.NotificationFeatureApiImpl
import com.mkirdev.unsplash.onboarding.api.OnboardingFeatureApi
import com.mkirdev.unsplash.onboarding.api.OnboardingFeatureApiImpl
import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi
import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApiImpl
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApi
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApiImpl
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApi
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApiImpl
import dagger.Binds
import dagger.Module

@Module
interface FeaturesModule {

    @Binds
    fun bindContentCreationFeature(featureApi: ContentCreationFeatureApiImpl): ContentCreationFeatureApi

    @Binds
    fun bindSocialCollectionsFeature(featureApi: SocialCollectionsFeatureApiImpl): SocialCollectionsFeatureApi

    @Binds
    fun bindUploadAndTrackFeature(featureApi: UploadAndTrackFeatureApiImpl): UploadAndTrackFeatureApi

    @Binds
    fun bindNotificationFeature(featureApi: NotificationFeatureApiImpl): NotificationFeatureApi

    @Binds
    fun bindOnboardingFeature(featureApi: OnboardingFeatureApiImpl): OnboardingFeatureApi

    @Binds
    fun bindAuthFeature(featureApi: AuthFeatureApiImpl): AuthFeatureApi

    @Binds
    fun bindBottomMenuFeature(featureApiImpl: BottomMenuFeatureApiImpl): BottomMenuFeatureApi

    @Binds
    fun bindPhotoFeedFeature(featureApi: PhotoFeedFeatureApiImpl): PhotoFeedFeatureApi

    @Binds
    fun bindDetailsFeature(featureApi: DetailsFeatureApiImpl): DetailsFeatureApi

    @Binds
    fun bindCollectionsFeature(featureApi: CollectionsFeatureApiImpl): CollectionsFeatureApi

    @Binds
    fun bindCollectionDetailsFeature(featureApi: CollectionDetailsFeatureApiImpl): CollectionDetailsFeatureApi

}