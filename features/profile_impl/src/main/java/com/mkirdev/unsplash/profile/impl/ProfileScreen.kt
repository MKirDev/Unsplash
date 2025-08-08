package com.mkirdev.unsplash.profile.impl

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.profile.models.ProfileModel
import com.mkirdev.unsplash.profile.preview.createPhotoItemModelsPreviewData
import com.mkirdev.unsplash.profile.preview.createProfileModelPreviewData
import com.mkirdev.unsplash.profile.widgets.MainContent

@Composable
internal fun ProfileScreenWrapper(
    uiState: ProfileContract.State,
    onPhotoItemClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onDownloadClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onCloseFieldClick: () -> Unit,
    onPagingCloseFieldClick: () -> Unit,
    onPagingRetry: (LazyPagingItems<PhotoItemModel>) -> Unit,
    onExitIconClick: () -> Unit,
    onCanceledLogoutClick: () -> Unit,
    onConfirmedLogoutClick: () -> Unit
) {

    val scrollIndex = rememberSaveable { mutableIntStateOf(0) }
    val scrollOffset = rememberSaveable { mutableIntStateOf(0) }


    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollIndex.intValue,
        initialFirstVisibleItemScrollOffset = scrollOffset.intValue
    )

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            scrollIndex.intValue = listState.firstVisibleItemIndex
            scrollOffset.intValue = listState.firstVisibleItemScrollOffset
        }
    }

    val profileModel = when (uiState) {
        is ProfileContract.State.Failure -> uiState.profileModel
        is ProfileContract.State.Success -> uiState.profileModel
        else -> null
    }

    val photoItemModels = when (uiState) {
        is ProfileContract.State.Failure -> uiState.photoItemModels
        is ProfileContract.State.Success -> uiState.photoItemModels
        else -> null
    }

    val isPagingLoadingError = when (uiState) {
        is ProfileContract.State.Failure -> uiState.isPagingLoadingError
        is ProfileContract.State.Success -> uiState.isPagingLoadingError
        else -> false
    }

    val isExitEnabled = when (uiState) {
        is ProfileContract.State.Failure -> uiState.isExitEnabled
        is ProfileContract.State.Success -> uiState.isExitEnabled
        else -> false
    }

    val errorText = when (uiState) {
        is ProfileContract.State.Failure -> uiState.error
        else -> null
    }

    photoItemModels?.let { flowPagingData ->
        val pagedItems = flowPagingData.collectAsLazyPagingItems()
        ProfileScreen(
            listState = listState,
            profileModel = profileModel,
            pagedItems = pagedItems,
            scrollIndex = scrollIndex.intValue,
            scrollOffset = scrollOffset.intValue,
            isPagingLoadingError = isPagingLoadingError,
            isExitEnabled = isExitEnabled,
            errorText = errorText,
            onPhotoItemClick = {
                scrollIndex.intValue = listState.firstVisibleItemIndex
                scrollOffset.intValue = listState.firstVisibleItemScrollOffset
                onPhotoItemClick
            },
            onLikeClick = onLikeClick,
            onRemoveLikeClick = onRemoveLikeClick,
            onDownloadClick = onDownloadClick,
            onLoadError = onLoadError,
            onCloseFieldClick = onCloseFieldClick,
            onPagingCloseFieldClick = onPagingCloseFieldClick,
            onPagingRetry = onPagingRetry,
            onExitIconClick = onExitIconClick,
            onCanceledLogoutClick = onCanceledLogoutClick,
            onConfirmedLogoutClick = onConfirmedLogoutClick
        )
    }


}

@Composable
fun ProfileScreen(
    listState: LazyListState,
    profileModel: ProfileModel?,
    pagedItems: LazyPagingItems<PhotoItemModel>,
    scrollIndex: Int,
    scrollOffset: Int,
    isPagingLoadingError: Boolean,
    isExitEnabled: Boolean,
    errorText: String?,
    onPhotoItemClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onDownloadClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onCloseFieldClick: () -> Unit,
    onPagingCloseFieldClick: () -> Unit,
    onPagingRetry: (LazyPagingItems<PhotoItemModel>) -> Unit,
    onExitIconClick: () -> Unit,
    onCanceledLogoutClick: () -> Unit,
    onConfirmedLogoutClick: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val offsetY = if (isPortrait) screenHeight * 0.847f else screenHeight * 0.648f

    Box(Modifier.fillMaxSize()) {
        MainContent(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            listState = listState,
            profileModel = profileModel,
            pagedItems = pagedItems,
            scrollIndex = scrollIndex,
            scrollOffset = scrollOffset,
            isPagingLoadingError = isPagingLoadingError,
            isExitEnabled = isExitEnabled,
            onPhotoItemClick = onPhotoItemClick,
            onLikeClick = onLikeClick,
            onRemoveLikeClick = onRemoveLikeClick,
            onDownloadClick = onDownloadClick,
            onLoadError = onLoadError,
            onPagingCloseFieldClick = onPagingCloseFieldClick,
            onPagingRetry = onPagingRetry,
            onExitIconClick = onExitIconClick,
            onCanceledLogoutClick = onCanceledLogoutClick,
            onConfirmedLogoutClick = onConfirmedLogoutClick
        )


    }
    if (!errorText.isNullOrEmpty()) {
        Box {
            ClosableErrorField(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = 0.dp, y = offsetY)
                    .zIndex(1f)
                    .alpha(0.9f)
                    .testTag(ProfileTags.ERROR_FIELD),
                text = errorText,
                textStyle = MaterialTheme.typography.bodyLargeMedium,
                onClick = onCloseFieldClick
            )
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    UnsplashTheme(dynamicColor = false) {
        ProfileScreenWrapper(
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
            onPagingRetry = {},
            onCanceledLogoutClick = {},
            onConfirmedLogoutClick = {}
        )
    }
}