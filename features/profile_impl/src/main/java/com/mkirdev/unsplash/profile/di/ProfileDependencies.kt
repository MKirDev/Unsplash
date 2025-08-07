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

interface ProfileDependencies {
    val getUserInfoUseCase: GetUserInfoUseCase
    val getLikedPhotosUseCase: GetLikedPhotosUseCase
    val likePhotoRemoteUseCase: LikePhotoRemoteUseCase
    val unlikePhotoRemoteUseCase: UnlikePhotoRemoteUseCase
    val addDownloadLinkUseCase: AddDownloadLinkUseCase
    val clearAuthTokensUseCase: ClearAuthTokensUseCase
    val clearPhotosStorageUseCase: ClearPhotosStorageUseCase
    val clearPhotosDatabaseUseCase: ClearPhotosDatabaseUseCase
    val clearCollectionsDatabaseUseCase: ClearCollectionsDatabaseUseCase
    val saveScheduleFlagUseCase: SaveScheduleFlagUseCase
}