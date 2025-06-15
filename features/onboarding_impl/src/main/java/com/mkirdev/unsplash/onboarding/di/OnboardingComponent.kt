package com.mkirdev.unsplash.onboarding.di

import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import com.mkirdev.unsplash.onboarding.impl.OnboardingViewModelFactory
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApi
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApi
import dagger.Component

@Component(modules = [OnboardingModule::class], dependencies = [OnboardingDependencies::class])
@OnboardingScope
internal interface OnboardingComponent : OnboardingDependencies {

    override val onboardingRepository: OnboardingRepository
    override val contentCreationFeatureApi: ContentCreationFeatureApi
    override val socialCollectionsFeatureApi: SocialCollectionsFeatureApi
    override val uploadAndTrackFeatureApi: UploadAndTrackFeatureApi
    val onboardingViewModelFactory: OnboardingViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: OnboardingDependencies): Builder
        fun build(): OnboardingComponent
    }

}