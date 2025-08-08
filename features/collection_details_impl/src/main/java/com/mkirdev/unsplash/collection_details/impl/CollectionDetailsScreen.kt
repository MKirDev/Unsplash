package com.mkirdev.unsplash.collection_details.impl

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mkirdev.unsplash.collection_details.models.CollectionDetailsModel
import com.mkirdev.unsplash.collection_details.preview.createCollectionDetailsPreviewData
import com.mkirdev.unsplash.collection_details.preview.createPhotoItemModelsPreviewData
import com.mkirdev.unsplash.collection_details.widgets.CollectionDetailsInfo
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.item_height_20
import com.mkirdev.unsplash.core.ui.theme.item_height_348
import com.mkirdev.unsplash.core.ui.theme.item_width_64
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.padding_70
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.HyperlinkText
import com.mkirdev.unsplash.core.ui.widgets.LikesInfoMedium
import com.mkirdev.unsplash.core.ui.widgets.LoadingIndicator
import com.mkirdev.unsplash.core.ui.widgets.StaticEmptyField
import com.mkirdev.unsplash.core.ui.widgets.TitleField
import com.mkirdev.unsplash.core.ui.widgets.UserImageMedium
import com.mkirdev.unsplash.core.ui.widgets.UserInfoMedium
import com.mkirdev.unsplash.photo_item.feature.PhotoItem
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take


private const val REPEAT_TIMES = 4

@Composable
internal fun CollectionDetailsScreenWrapper(
    uiState: CollectionDetailsContract.State,
    onPhotoItemClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onDownloadClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onCloseFieldClick: () -> Unit,
    onPagingCloseFieldClick: () -> Unit,
    onPagingRetry: (LazyPagingItems<PhotoItemModel>) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateBack: () -> Unit
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

    val collectionModel = when (uiState) {
        is CollectionDetailsContract.State.Success -> uiState.collectionDetailsModel
        is CollectionDetailsContract.State.Failure -> uiState.collectionDetailsModel
        else -> null
    }

    val photoModels = when (uiState) {
        is CollectionDetailsContract.State.Success -> uiState.photoItemModels
        is CollectionDetailsContract.State.Failure -> uiState.photoItemModels
        else -> null
    }

    val errorText = when (uiState) {
        is CollectionDetailsContract.State.Success -> null
        is CollectionDetailsContract.State.Failure -> uiState.error
        else -> null
    }


    val isPagingError = when (uiState) {
        is CollectionDetailsContract.State.Success -> uiState.isPagingLoadingError
        is CollectionDetailsContract.State.Failure -> uiState.isPagingLoadingError
        else -> false
    }

    photoModels?.let { flowPagingData ->
        val pagedItems = flowPagingData.collectAsLazyPagingItems()
        CollectionDetailsScreen(
            listState = listState,
            collectionModel = collectionModel,
            pagedItems = pagedItems,
            scrollIndex = scrollIndex.intValue,
            scrollOffset = scrollOffset.intValue,
            isPagingLoadingError = isPagingError,
            errorText = errorText,
            onPhotoItemClick = { id ->
                scrollIndex.intValue = listState.firstVisibleItemIndex
                scrollOffset.intValue = listState.firstVisibleItemScrollOffset
                onPhotoItemClick(id)
            },
            onLikeClick = onLikeClick,
            onRemoveLikeClick = onRemoveLikeClick,
            onDownloadClick = onDownloadClick,
            onLoadError = onLoadError,
            onCloseFieldClick = onCloseFieldClick,
            onPagingCloseFieldClick = onPagingCloseFieldClick,
            onPagingRetry = onPagingRetry,
            onNavigateUp = onNavigateUp,
            onNavigateBack = onNavigateBack
        )
    }

}

@Composable
private fun CollectionDetailsScreen(
    listState: LazyListState,
    collectionModel: CollectionDetailsModel?,
    pagedItems: LazyPagingItems<PhotoItemModel>,
    scrollIndex: Int,
    scrollOffset: Int,
    isPagingLoadingError: Boolean,
    errorText: String?,
    onPhotoItemClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onDownloadClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onCloseFieldClick: () -> Unit,
    onPagingCloseFieldClick: () -> Unit,
    onPagingRetry: (LazyPagingItems<PhotoItemModel>) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateBack: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val offsetY = if (isPortrait) screenHeight * 0.870f else screenHeight * 0.648f

    BackHandler(enabled = true) {
        onNavigateBack()
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagedItems.itemCount }
            .filter { it > scrollIndex }
            .take(1)
            .collect {
                listState.scrollToItem(scrollIndex, scrollOffset)
            }
    }

    LaunchedEffect(key1 = pagedItems.loadState, block = {
        snapshotFlow { pagedItems.loadState.append }.collect { loadState ->
            if (loadState is LoadState.Error) onLoadError()
            else if (loadState is LoadState.Loading && isPagingLoadingError) onPagingCloseFieldClick()
        }
    })

    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
        Column {
            TitleField(
                titleText = stringResource(id = R.string.collection_details_title),
                modifier = Modifier.fillMaxWidth(),
                onNavigateUp = onNavigateUp
            )
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .testTag(CollectionDetailsTags.LAZY_COLUMN),
                state = listState
            ) {
                when (pagedItems.loadState.refresh) {
                    LoadState.Loading -> {
                        repeat(REPEAT_TIMES) {
                            item {
                                StaticEmptyField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = padding_70)
                                )
                            }
                        }
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingIndicator(
                                    modifier = Modifier
                                        .width(item_width_64)
                                        .padding(bottom = padding_6)
                                )
                            }
                        }
                        item {
                            StaticEmptyField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = padding_70)
                            )
                        }
                    }

                    else -> {
                        collectionModel?.let {
                            item {
                                CollectionDetailsInfo(collectionDetailsModel = it)
                            }
                        }
                        items(
                            pagedItems.itemCount,
                            key = pagedItems.itemKey { photoItemModel -> photoItemModel.photoId }) { index ->
                            val item = pagedItems[index]
                            item?.let {
                                PhotoItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(item_height_348)
                                        .clickable {
                                            onPhotoItemClick(it.photoId)
                                        },
                                    contentScale = ContentScale.FillBounds,
                                    photoItemModel = it,
                                    userImage = {
                                        UserImageMedium(imageUrl = it.user.userImage)
                                    },
                                    userInfo = {
                                        UserInfoMedium(
                                            name = it.user.name,
                                            userName = it.user.username
                                        )
                                    },
                                    likesInfo = { modifier, onLike, onRemoveLike ->
                                        LikesInfoMedium(
                                            modifier = modifier.padding(
                                                end = padding_6,
                                                bottom = padding_10
                                            ),
                                            photoId = it.photoId,
                                            likes = it.likes,
                                            isLikedPhoto = it.isLiked,
                                            onLikeClick = onLike,
                                            onRemoveLikeClick = onRemoveLike
                                        )
                                    },
                                    downloadText = { modifier, onDownload ->

                                        HyperlinkText(
                                            downloadText = stringResource(id = R.string.download),
                                            downloadLink = it.downloadLink,
                                            downloads = it.downloads,
                                            modifier = modifier
                                                .padding(bottom = padding_10)
                                                .height(item_height_20),
                                            textStyle = MaterialTheme.typography.bodyLarge,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onSecondary,
                                            onDownloadClick = onDownload
                                        )
                                    },
                                    onLikeClick = onLikeClick,
                                    onRemoveLikeClick = onRemoveLikeClick,
                                    onDownloadClick = onDownloadClick
                                )
                            }
                        }

                        if (isPagingLoadingError) {
                            item {
                                ClosableErrorField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(padding_6)
                                        .testTag(CollectionDetailsTags.PAGING_ERROR_FIELD),
                                    text = stringResource(id = R.string.collections_network_error),
                                    textStyle = MaterialTheme.typography.bodyLargeMedium,
                                    onClick = {
                                        onPagingRetry(pagedItems)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    if (!errorText.isNullOrEmpty()) {
        Box {
            ClosableErrorField(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = 0.dp, y = offsetY)
                    .zIndex(1f)
                    .alpha(0.9f)
                    .testTag(CollectionDetailsTags.ERROR_FIELD),
                text = errorText,
                textStyle = MaterialTheme.typography.bodyLargeMedium,
                onClick = onCloseFieldClick
            )
        }
    }
}


internal object CollectionDetailsTags {
    const val LAZY_COLUMN = "CollectionDetailsTags:LAZY_COLUMN"
    const val LOADING_INDICATOR = "CollectionDetailsTags:LOADING_INDICATOR"
    const val ERROR_FIELD = "CollectionDetailsTags:ERROR_FIELD"
    const val PAGING_ERROR_FIELD = "CollectionDetailsTags:PAGING_ERROR_FIELD"
}

@Preview
@Composable
private fun CollectionDetailsScreenPreview() {
    UnsplashTheme(dynamicColor = false) {
        CollectionDetailsScreenWrapper(
            uiState = CollectionDetailsContract.State.Failure(
                error = stringResource(id = R.string.preview_error),
                isPagingLoadingError = true,
                updatedCount = 0,
                collectionDetailsModel = createCollectionDetailsPreviewData(),
                photoItemModels = createPhotoItemModelsPreviewData()
            ),
            onPhotoItemClick = {},
            onLikeClick = {},
            onRemoveLikeClick = {},
            onDownloadClick = {},
            onLoadError = {},
            onCloseFieldClick = {},
            onPagingCloseFieldClick = {},
            onPagingRetry = {},
            onNavigateUp = {},
            onNavigateBack = {}
        )
    }
}