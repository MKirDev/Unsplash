package com.mkirdev.unsplash.onboarding.di

import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import com.mkirdev.unsplash.domain.usecases.onboarding.GetOnboardingFlagUseCase
import com.mkirdev.unsplash.domain.usecases.onboarding.SaveOnboardingFlagUseCase
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
    val saveOnboardingFlagUseCase: SaveOnboardingFlagUseCase
    val getOnboardingFlagUseCase: GetOnboardingFlagUseCase

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: OnboardingDependencies): Builder
        fun build(): OnboardingComponent
    }

}