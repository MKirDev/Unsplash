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
import androidx.compose.ui.tooling.preview.Preview
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.details.models.DetailsModel
import com.mkirdev.unsplash.details.preview.createPhotoDetailsPreview
import com.mkirdev.unsplash.details.widgets.MainContent

@Composable
internal fun PhotoDetailsScreenWrapper(
    uiState: DetailsContract.State,
    onShareClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onLocationClick: (CoordinatesModel) -> Unit,
    onDownloadClick: (String) -> Unit,
    onCloseFieldClick: () -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val model = when (uiState) {
        is DetailsContract.State.Failure -> uiState.detailsModel
        is DetailsContract.State.Success -> uiState.detailsModel
        DetailsContract.State.Idle -> null
    }

    val errorText = when (uiState) {
        is DetailsContract.State.Failure -> uiState.error
        else -> null
    }


    model?.let {
        PhotoDetailsScreen(
            model = it,
            errorText = errorText,
            onShareClick = onShareClick,
            onLikeClick = onLikeClick,
            onRemoveLikeClick = onRemoveLikeClick,
            onLocationClick = onLocationClick,
            onDownloadClick = onDownloadClick,
            onCloseFieldClick = onCloseFieldClick,
            onNavigateUp = onNavigateUp,
            onNavigateBack = onNavigateBack
        )
    }
}

@Composable
private fun PhotoDetailsScreen(
    model: DetailsModel,
    errorText: String?,
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
        MainContent(
            modifier = Modifier.testTag(PhotoDetailsTags.MAIN_CONTENT),
            detailsModel = model,
            onShareClick = onShareClick,
            onLikeClick = onLikeClick,
            onRemoveLikeClick = onRemoveLikeClick,
            onLocationClick = onLocationClick,
            onDownloadClick = onDownloadClick,
            onNavigateUp = onNavigateUp
        )
        errorText?.let {
            ClosableErrorField(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .alpha(0.9f)
                    .testTag(PhotoDetailsTags.ERROR_FIELD),
                text = it,
                textStyle = MaterialTheme.typography.bodyLargeMedium,
                onClick = onCloseFieldClick
            )
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
        PhotoDetailsScreenWrapper(
            uiState = DetailsContract.State.Success(
                detailsModel = createPhotoDetailsPreview()
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