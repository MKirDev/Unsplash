package com.mkirdev.unsplash.profile.di

import com.mkirdev.unsplash.domain.usecases.auth.ClearAuthTokensUseCase
import com.mkirdev.unsplash.domain.usecases.collections.ClearCollectionsDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.ClearPhotosDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.photos.ClearPhotosStorageUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.preferences.SaveScheduleFlagUseCase
import com.mkirdev.unsplash.domain.usecases.user.ClearUserDatabaseUseCase
import com.mkirdev.unsplash.domain.usecases.user.GetLikedPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.user.GetCurrentUserUseCase
import com.mkirdev.unsplash.profile.impl.ProfileViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal class ProfileModule {

    @Provides
    fun provideProfileViewModelFactory(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        getLikedPhotosUseCase: GetLikedPhotosUseCase,
        likePhotoLocalUseCase: LikePhotoLocalUseCase,
        unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
        addDownloadLinkUseCase: AddDownloadLinkUseCase,
        saveScheduleFlagUseCase: SaveScheduleFlagUseCase,
        clearAuthTokensUseCase: ClearAuthTokensUseCase,
        clearPhotosStorageUseCase: ClearPhotosStorageUseCase,
        clearPhotosDatabaseUseCase: ClearPhotosDatabaseUseCase,
        clearCollectionsDatabaseUseCase: ClearCollectionsDatabaseUseCase,
        clearUserDatabaseUseCase: ClearUserDatabaseUseCase
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(
            getCurrentUserUseCase = getCurrentUserUseCase,
            getLikedPhotosUseCase = getLikedPhotosUseCase,
            likePhotoLocalUseCase = likePhotoLocalUseCase,
            unlikePhotoLocalUseCase = unlikePhotoLocalUseCase,
            addDownloadLinkUseCase = addDownloadLinkUseCase,
            saveScheduleFlagUseCase = saveScheduleFlagUseCase,
            clearAuthTokensUseCase = clearAuthTokensUseCase,
            clearPhotosStorageUseCase = clearPhotosStorageUseCase,
            clearPhotosDatabaseUseCase = clearPhotosDatabaseUseCase,
            clearCollectionsDatabaseUseCase = clearCollectionsDatabaseUseCase,
            clearUserDatabaseUseCase = clearUserDatabaseUseCase
        )
    }
}