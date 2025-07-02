package com.mkirdev.unsplash.di

import android.content.Context
import com.mkirdev.unsplash.app.DataStoreManager
import com.mkirdev.unsplash.auth.api.AuthFeatureApi
import com.mkirdev.unsplash.auth.di.AuthDependencies
import com.mkirdev.unsplash.bottom_menu.api.BottomMenuFeatureApi
import com.mkirdev.unsplash.bottom_menu.di.BottomMenuDependencies
import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.core.navigation.TopDestinations
import com.mkirdev.unsplash.domain.repository.AuthRepository
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import com.mkirdev.unsplash.domain.repository.PhotosRepository
import com.mkirdev.unsplash.domain.usecases.photos.GetLikedPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetUnlikedPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoRemoteUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoRemoteUseCase
import com.mkirdev.unsplash.onboarding.api.OnboardingFeatureApi
import com.mkirdev.unsplash.onboarding.di.OnboardingDependencies
import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi
import com.mkirdev.unsplash.photo_feed.di.PhotoFeedDependencies
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApi
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApi
import dagger.BindsInstance
import dagger.Component
import net.openid.appauth.AuthorizationService
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        StorageModule::class,
        RepositoriesModule::class,
        AppModule::class,
        FeaturesModule::class
    ]
)
interface AppComponent : OnboardingDependencies, AuthDependencies, BottomMenuDependencies,
    PhotoFeedDependencies {

    override val onboardingRepository: OnboardingRepository
    override val contentCreationFeatureApi: ContentCreationFeatureApi
    override val socialCollectionsFeatureApi: SocialCollectionsFeatureApi
    override val uploadAndTrackFeatureApi: UploadAndTrackFeatureApi

    val onboardingFeatureApi: OnboardingFeatureApi

    override val authRepository: AuthRepository
    override val authService: AuthorizationService

    val authFeatureApi: AuthFeatureApi

    val bottomMenuFeatureApi: BottomMenuFeatureApi

    override val photoFeedFeatureApi: PhotoFeedFeatureApi
    override val topLevelDestination: TopDestinations

    override val photosRepository: PhotosRepository

    val likePhotoRemoteUseCase: LikePhotoRemoteUseCase

    val unlikePhotoRemoteUseCase: UnlikePhotoRemoteUseCase

    val getLikedPhotoUseCase: GetLikedPhotoUseCase

    val getUnlikedPhotoUseCase: GetUnlikedPhotoUseCase

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun dataStoreManager(manager: DataStoreManager): Builder

        fun build(): AppComponent
    }

}