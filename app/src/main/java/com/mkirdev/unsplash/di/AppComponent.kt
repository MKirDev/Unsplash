package com.mkirdev.unsplash.di

import android.content.Context
import com.mkirdev.unsplash.app.DataStoreManager
import com.mkirdev.unsplash.auth.api.AuthFeatureApi
import com.mkirdev.unsplash.auth.di.AuthDependencies
import com.mkirdev.unsplash.bottom_menu.api.BottomMenuFeatureApi
import com.mkirdev.unsplash.bottom_menu.di.BottomMenuDependencies
import com.mkirdev.unsplash.collection_details.api.CollectionDetailsFeatureApi
import com.mkirdev.unsplash.collection_details.di.CollectionDetailsDependencies
import com.mkirdev.unsplash.collections.api.CollectionsFeatureApi
import com.mkirdev.unsplash.collections.di.CollectionsDependencies
import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.core.navigation.TopDestinations
import com.mkirdev.unsplash.data.network.photos.api.DownloadApi
import com.mkirdev.unsplash.details.api.DetailsFeatureApi
import com.mkirdev.unsplash.details.di.DetailsDependencies
import com.mkirdev.unsplash.domain.repository.AuthRepository
import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import com.mkirdev.unsplash.domain.usecases.auth.ClearAuthTokensUseCase
import com.mkirdev.unsplash.domain.usecases.collections.ClearCollectionsDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionInfoUseCase
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionsUseCase
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.ClearPhotosDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.photos.ClearPhotosStorageUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetLikedPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetUnlikedPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoRemoteUseCase
import com.mkirdev.unsplash.domain.usecases.photos.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoRemoteUseCase
import com.mkirdev.unsplash.domain.usecases.preferences.DeleteScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.preferences.GetScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.preferences.SaveScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.user.AddCurrentUserUseCase
import com.mkirdev.unsplash.domain.usecases.user.ClearUserDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.user.GetLikedPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.user.GetCurrentUserUseCase
import com.mkirdev.unsplash.notification.api.NotificationFeatureApi
import com.mkirdev.unsplash.onboarding.api.OnboardingFeatureApi
import com.mkirdev.unsplash.onboarding.di.OnboardingDependencies
import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi
import com.mkirdev.unsplash.photo_feed.di.PhotoFeedDependencies
import com.mkirdev.unsplash.profile.api.ProfileFeatureApi
import com.mkirdev.unsplash.profile.di.ProfileDependencies
import com.mkirdev.unsplash.schedulers.CacheScheduler
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApi
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApi
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import net.openid.appauth.AuthorizationService
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        StorageModule::class,
        RepositoriesModule::class,
        DomainModule::class,
        AppModule::class,
        FeaturesModule::class
    ]
)
interface AppComponent : OnboardingDependencies, AuthDependencies, BottomMenuDependencies,
    PhotoFeedDependencies, DetailsDependencies, CollectionsDependencies,
    CollectionDetailsDependencies, ProfileDependencies {

    override val onboardingRepository: OnboardingRepository
    override val contentCreationFeatureApi: ContentCreationFeatureApi
    override val socialCollectionsFeatureApi: SocialCollectionsFeatureApi
    override val uploadAndTrackFeatureApi: UploadAndTrackFeatureApi

    override val notificationFeatureApi: NotificationFeatureApi

    val onboardingFeatureApi: OnboardingFeatureApi

    override val authRepository: AuthRepository
    override val authService: AuthorizationService

    override val clearAuthTokensUseCase: ClearAuthTokensUseCase

    val authFeatureApi: AuthFeatureApi

    val bottomMenuFeatureApi: BottomMenuFeatureApi

    override val photoFeedFeatureApi: PhotoFeedFeatureApi

    override val collectionsFeatureApi: CollectionsFeatureApi

    override val profileFeatureApi: ProfileFeatureApi

    override val topLevelDestination: TopDestinations

    override val getPhotosUseCase: GetPhotosUseCase
    override val searchPhotosUseCase: SearchPhotosUseCase
    override val likePhotoLocalUseCase: LikePhotoLocalUseCase
    override val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase
    override val addDownloadLinkUseCase: AddDownloadLinkUseCase

    override val getPhotoUseCase: GetPhotoUseCase

    override val clearPhotosStorageUseCase: ClearPhotosStorageUseCase

    override val clearPhotosDatabaseUseCase: ClearPhotosDatabaseUseCase

    override val clearUserDatabaseUseCase: ClearUserDatabaseUseCase

    override val getCurrentUserUseCase: GetCurrentUserUseCase

    override val addCurrentUserUseCase: AddCurrentUserUseCase

    override val getLikedPhotosUseCase: GetLikedPhotosUseCase

    override val getCollectionsUseCase: GetCollectionsUseCase

    override val getCollectionInfoUseCase: GetCollectionInfoUseCase

    override val getCollectionPhotosUseCase: GetCollectionPhotosUseCase

    override val clearCollectionsDatabaseUseCase: ClearCollectionsDatabaseUseCase

    val likePhotoRemoteUseCase: LikePhotoRemoteUseCase
    val unlikePhotoRemoteUseCase: UnlikePhotoRemoteUseCase

    val getLikedPhotoUseCase: GetLikedPhotoUseCase
    val getUnlikedPhotoUseCase: GetUnlikedPhotoUseCase

    val detailsFeatureApi: DetailsFeatureApi

    val collectionDetailsFeatureApi: CollectionDetailsFeatureApi

    val downloadApi: DownloadApi

    val coroutineDispatcher: CoroutineDispatcher

    val getDownloadLinkUseCase: GetDownloadLinkUseCase

    val cacheScheduler: CacheScheduler

    override val saveScheduleFlagUseCase: SaveScheduleFlagUseCase

    val getScheduleFlagUseCase: GetScheduleFlagUseCase

    override val deleteScheduleFlagUseCase: DeleteScheduleFlagUseCase

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun dataStoreManager(manager: DataStoreManager): Builder

        fun build(): AppComponent
    }

}