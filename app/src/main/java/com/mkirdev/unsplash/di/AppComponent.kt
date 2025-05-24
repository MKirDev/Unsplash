package com.mkirdev.unsplash.di

import android.content.Context
import com.mkirdev.unsplash.app.DataStoreManager
import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import com.mkirdev.unsplash.onboarding.api.OnboardingFeatureApi
import com.mkirdev.unsplash.onboarding.di.OnboardingDependencies
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApi
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        StorageModule::class,
        RepositoriesModule::class,
        AppModule::class,
        FeaturesModule::class
    ]
)
interface AppComponent : OnboardingDependencies {

    override val onboardingRepository: OnboardingRepository
    override val contentCreationFeatureApi: ContentCreationFeatureApi
    override val socialCollectionsFeatureApi: SocialCollectionsFeatureApi
    override val uploadAndTrackFeatureApi: UploadAndTrackFeatureApi

    val onboardingFeatureApi: OnboardingFeatureApi

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun dataStoreManager(manager: DataStoreManager): Builder

        fun build(): AppComponent
    }

}