package com.mkirdev.unsplash.profile.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.item_width_64
import com.mkirdev.unsplash.core.ui.widgets.LoadingIndicator
import com.mkirdev.unsplash.profile.preview.createPhotoItemModelsPreviewData
import com.mkirdev.unsplash.profile.preview.createProfileModelPreviewData
import com.mkirdev.unsplash.profile.widgets.LogoutColumn
import com.mkirdev.unsplash.profile.widgets.MainContentFailure
import com.mkirdev.unsplash.profile.widgets.MainContentSuccess

@Composable
fun ProfileScreen(
    uiState: ProfileContract.State,
    onPhotoItemClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onDownloadClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onCloseFieldClick: () -> Unit,
    onPagingCloseFieldClick: () -> Unit,
    onExitIconClick: () -> Unit,
    onCanceledLogoutClick: () -> Unit,
    onConfirmedLogoutClick: () -> Unit
) {
    when (uiState) {
        ProfileContract.State.Idle -> {}
        ProfileContract.State.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingIndicator(
                    modifier = Modifier
                        .width(item_width_64)
                        .testTag(ProfileTags.LOADING_INDICATOR)
                )
            }
        }

        is ProfileContract.State.Failure -> {
            Box {
                uiState.profileModel?.let {
                    uiState.photoItemModels?.let { it1 ->
                        MainContentFailure(
                            modifier = Modifier.background(MaterialTheme.colorScheme.background),
                            error = uiState.error,
                            profileModel = it,
                            photoItemModels = it1,
                            isPagingLoadingError = uiState.isPagingLoadingError,
                            onExitIconClick = onExitIconClick,
                            onPhotoItemClick = onPhotoItemClick,
                            onLikeClick = onLikeClick,
                            onRemoveLikeClick = onRemoveLikeClick,
                            onDownloadClick = onDownloadClick,
                            onLoadError = onLoadError,
                            onCloseFieldClick = onCloseFieldClick
                        )
                    }
                }
                when (uiState.isExitEnabled) {
                    true -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(enabled = false) {}
                        )
                        LogoutColumn(
                            modifier = Modifier.testTag(ProfileTags.LOGOUT_COLUMN),
                            onCanceledClick = onCanceledLogoutClick,
                            onConfirmedClick = onConfirmedLogoutClick
                        )

                    }
                    false -> {}
                }
            }
        }
        is ProfileContract.State.Success -> {
            Box {
                MainContentSuccess(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    profileModel = uiState.profileModel,
                    photoItemModels = uiState.photoItemModels,
                    isPagingLoadingError = uiState.isPagingLoadingError,
                    onExitIconClick = onExitIconClick,
                    onPhotoItemClick = onPhotoItemClick,
                    onLikeClick = onLikeClick,
                    onRemoveLikeClick = onRemoveLikeClick,
                    onDownloadClick = onDownloadClick,
                    onLoadError = onLoadError,
                    onPagingCloseFieldClick = onPagingCloseFieldClick
                )
                when (uiState.isExitEnabled) {
                    true -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(enabled = false) {}
                        )
                        LogoutColumn(
                            modifier = Modifier.testTag(ProfileTags.LOGOUT_COLUMN),
                            onCanceledClick = onCanceledLogoutClick,
                            onConfirmedClick = onConfirmedLogoutClick
                        )

                    }
                    false -> {}
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    UnsplashTheme(dynamicColor = false) {
        ProfileScreen(
            uiState = ProfileContract.State.Failure(
                error = "Preview error",
                profileModel = createProfileModelPreviewData(),
                photoItemModels = createPhotoItemModelsPreviewData(),
                isPagingLoadingError = true,
                isExitEnabled = false,
                updatedCount = 0
            ),
            onExitIconClick = { },
            onPhotoItemClick = {},
            onLikeClick = {},
            onRemoveLikeClick = {},
            onDownloadClick = {},
            onLoadError = { },
            onCloseFieldClick = {},
            onPagingCloseFieldClick = {},
            onCanceledLogoutClick = {},
            onConfirmedLogoutClick = {}
        )
    }
}