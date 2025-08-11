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

interface ProfileDependencies {
    val getCurrentUserUseCase: GetCurrentUserUseCase
    val getLikedPhotosUseCase: GetLikedPhotosUseCase
    val likePhotoLocalUseCase: LikePhotoLocalUseCase
    val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase
    val addDownloadLinkUseCase: AddDownloadLinkUseCase
    val clearAuthTokensUseCase: ClearAuthTokensUseCase
    val clearPhotosStorageUseCase: ClearPhotosStorageUseCase
    val clearPhotosDatabaseUseCase: ClearPhotosDatabaseUseCase
    val clearCollectionsDatabaseUseCase: ClearCollectionsDatabaseUseCase

    val clearUserDatabaseUseCase: ClearUserDatabaseUseCase
    val saveScheduleFlagUseCase: SaveScheduleFlagUseCase
}