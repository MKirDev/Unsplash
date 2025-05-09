package com.mkirdev.unsplash.details.impl

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.ClosableInfoField
import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.details.preview.createPhotoDetailsPreview
import com.mkirdev.unsplash.details.widgets.MainContent

@Composable
fun PhotoDetailsScreen(
    uiState: PhotoDetailsContract.State,
    onShareClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onLocationClick: (CoordinatesModel) -> Unit,
    onDownloadClick: (String) -> Unit,
    onCloseFieldClick: () -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        BackHandler(enabled = true) {
            onNavigateBack()
        }
        when (uiState) {
            is PhotoDetailsContract.State.DownloadFailure -> {
                MainContent(
                    modifier = Modifier.testTag(PhotoDetailsTags.MAIN_CONTENT),
                    photoDetailsModel = uiState.photoDetailsModel,
                    onShare = onShareClick,
                    onLike = onLikeClick,
                    onRemoveLike = onRemoveLikeClick,
                    onLocation = onLocationClick,
                    onDownload = onDownloadClick,
                    onNavigateUp = onNavigateUp
                )
                ClosableErrorField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .alpha(0.9f)
                        .testTag(PhotoDetailsTags.DOWNLOAD_ERROR_FIELD),
                    text = stringResource(id = R.string.download_photo_network_error),
                    textStyle = MaterialTheme.typography.bodyLargeMedium,
                    onClick = onCloseFieldClick
                )
            }

            is PhotoDetailsContract.State.DownloadSuccess -> {
                MainContent(
                    modifier = Modifier.testTag(PhotoDetailsTags.MAIN_CONTENT),
                    photoDetailsModel = uiState.photoDetailsModel,
                    onShare = onShareClick,
                    onLike = onLikeClick,
                    onRemoveLike = onRemoveLikeClick,
                    onLocation = onLocationClick,
                    onDownload = onDownloadClick,
                    onNavigateUp = onNavigateUp
                )
                ClosableInfoField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .alpha(0.9f)
                        .testTag(PhotoDetailsTags.DOWNLOAD_INFO_FIELD),
                    text = stringResource(id = R.string.download_photo_success),
                    textStyle = MaterialTheme.typography.bodyLargeMedium,
                    onClick = onCloseFieldClick
                )
            }

            is PhotoDetailsContract.State.Failure -> {
                MainContent(
                    modifier = Modifier.testTag(PhotoDetailsTags.MAIN_CONTENT),
                    photoDetailsModel = uiState.photoDetailsModel,
                    onShare = onShareClick,
                    onLike = onLikeClick,
                    onRemoveLike = onRemoveLikeClick,
                    onLocation = onLocationClick,
                    onDownload = onDownloadClick,
                    onNavigateUp = onNavigateUp
                )
                ClosableErrorField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .alpha(0.9f)
                        .testTag(PhotoDetailsTags.ERROR_FIELD),
                    text = uiState.error,
                    textStyle = MaterialTheme.typography.bodyLargeMedium,
                    onClick = onCloseFieldClick
                )
            }

            is PhotoDetailsContract.State.Success -> {
                MainContent(
                    modifier = Modifier.testTag(PhotoDetailsTags.MAIN_CONTENT),
                    photoDetailsModel = uiState.photoDetailsModel,
                    onShare = onShareClick,
                    onLike = onLikeClick,
                    onRemoveLike = onRemoveLikeClick,
                    onLocation = onLocationClick,
                    onDownload = onDownloadClick,
                    onNavigateUp = onNavigateUp
                )
            }

            PhotoDetailsContract.State.Idle -> {}
        }
    }
}

object PhotoDetailsTags {
    const val ERROR_FIELD = "PhotoDetailsTags:ERROR_FIELD"
    const val DOWNLOAD_ERROR_FIELD = "PhotoDetailsTags:ERROR_FIELD"
    const val DOWNLOAD_INFO_FIELD = "PhotoDetailsTags:INFO_FIELD"
    const val MAIN_CONTENT = "PhotoDetailsTags:MAIN_CONTENT"
}

@Preview
@Composable
private fun PhotoDetailsScreenPreview() {
    UnsplashTheme(dynamicColor = false) {
        PhotoDetailsScreen(
            uiState = PhotoDetailsContract.State.DownloadSuccess(
                photoDetailsModel = createPhotoDetailsPreview()
            ),
            onShareClick = {},
            onLikeClick = {},
            onRemoveLikeClick = {},
            onLocationClick = {},
            onDownloadClick = {},
            onNavigateUp = {},
            onNavigateBack = {},
            onCloseFieldClick = {}
        )
    }
}