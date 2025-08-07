package com.mkirdev.unsplash.profile.di

import com.mkirdev.unsplash.domain.usecases.auth.ClearAuthTokensUseCase
import com.mkirdev.unsplash.domain.usecases.collections.ClearCollectionsDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.ClearPhotosDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.photos.ClearPhotosStorageUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoRemoteUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoRemoteUseCase
import com.mkirdev.unsplash.domain.usecases.preferences.SaveScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.user.GetLikedPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.user.GetUserInfoUseCase
import com.mkirdev.unsplash.profile.impl.ProfileViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal class ProfileModule {

    @Provides
    fun provideProfileViewModelFactory(
        getUserInfoUseCase: GetUserInfoUseCase,
        getLikedPhotosUseCase: GetLikedPhotosUseCase,
        likePhotoRemoteUseCase: LikePhotoRemoteUseCase,
        unlikePhotoRemoteUseCase: UnlikePhotoRemoteUseCase,
        addDownloadLinkUseCase: AddDownloadLinkUseCase,
        saveScheduleFlagUseCase: SaveScheduleFlagUseCase,
        clearAuthTokensUseCase: ClearAuthTokensUseCase,
        clearPhotosStorageUseCase: ClearPhotosStorageUseCase,
        clearPhotosDatabaseUseCase: ClearPhotosDatabaseUseCase,
        clearCollectionsDatabaseUseCase: ClearCollectionsDatabaseUseCase
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(
            getUserInfoUseCase = getUserInfoUseCase,
            getLikedPhotosUseCase = getLikedPhotosUseCase,
            likePhotoRemoteUseCase = likePhotoRemoteUseCase,
            unlikePhotoRemoteUseCase = unlikePhotoRemoteUseCase,
            addDownloadLinkUseCase = addDownloadLinkUseCase,
            saveScheduleFlagUseCase = saveScheduleFlagUseCase,
            clearAuthTokensUseCase = clearAuthTokensUseCase,
            clearPhotosStorageUseCase = clearPhotosStorageUseCase,
            clearPhotosDatabaseUseCase = clearPhotosDatabaseUseCase,
            clearCollectionsDatabaseUseCase = clearCollectionsDatabaseUseCase
        )
    }
}