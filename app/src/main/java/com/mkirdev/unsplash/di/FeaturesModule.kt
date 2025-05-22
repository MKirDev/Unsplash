package com.mkirdev.unsplash.di

import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApiImpl
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

}