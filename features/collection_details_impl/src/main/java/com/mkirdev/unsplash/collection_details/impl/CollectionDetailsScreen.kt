package com.mkirdev.unsplash.collection_details.impl

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mkirdev.unsplash.collection_details.preview.createCollectionDetailsPreviewData
import com.mkirdev.unsplash.collection_details.preview.createPhotoItemModelsPreviewData
import com.mkirdev.unsplash.collection_details.widgets.CollectionDetailsInfo
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.UnsplashTheme
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.item_width_64
import com.mkirdev.unsplash.core.ui.theme.padding_10
import com.mkirdev.unsplash.core.ui.theme.padding_16
import com.mkirdev.unsplash.core.ui.theme.padding_6
import com.mkirdev.unsplash.core.ui.theme.padding_60
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.HyperlinkText
import com.mkirdev.unsplash.core.ui.widgets.LikesInfoSmall
import com.mkirdev.unsplash.core.ui.widgets.LoadingIndicator
import com.mkirdev.unsplash.core.ui.widgets.StaticErrorField
import com.mkirdev.unsplash.core.ui.widgets.TitleField
import com.mkirdev.unsplash.core.ui.widgets.UserImageMedium
import com.mkirdev.unsplash.core.ui.widgets.UserImageSmall
import com.mkirdev.unsplash.core.ui.widgets.UserInfoMedium
import com.mkirdev.unsplash.core.ui.widgets.UserInfoSmall
import com.mkirdev.unsplash.photo_item.feature.PhotoItem
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel

@Composable
fun CollectionDetailsScreen(
    uiState: CollectionDetailsContract.State,
    onPhotoItemClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onRemoveLikeClick: (String) -> Unit,
    onDownloadClick: (String) -> Unit,
    onLoadError: () -> Unit,
    onCloseFieldClick: () -> Unit,
    onPagingCloseFieldClick: () -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateBack: () -> Unit
) {
    BackHandler(enabled = true) {
        onNavigateBack()
    }
    when (uiState) {
        CollectionDetailsContract.State.Idle -> {}
        CollectionDetailsContract.State.Loading -> {
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
                        .testTag(CollectionDetailsTags.LOADING_INDICATOR)
                )
            }
        }

        is CollectionDetailsContract.State.Failure -> {
            Box {
                Column {
                    TitleField(
                        titleText = stringResource(id = R.string.collection_details_title),
                        modifier = Modifier.fillMaxWidth(),
                        onNavigateUp = onNavigateUp
                    )
                    uiState.photoItemModels?.let { flowPagingData ->
                        val pagedItems: LazyPagingItems<PhotoItemModel> =
                            flowPagingData.collectAsLazyPagingItems()
                        LazyColumn(modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondary)
                            .testTag(CollectionDetailsTags.LAZY_COLUMN)
                        ) {
                            item {
                                uiState.collectionDetailsModel?.let {
                                    CollectionDetailsInfo(collectionDetailsModel = it)
                                }
                            }
                            items(
                                pagedItems.itemCount,
                                key = pagedItems.itemKey { collectionItem -> collectionItem.id }) { index ->
                                val item = pagedItems[index]
                                item?.let {
                                    PhotoItem(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                onPhotoItemClick(it.id)
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
                                            LikesInfoSmall(
                                                modifier = modifier.padding(
                                                    end = padding_6,
                                                    bottom = padding_10
                                                ),
                                                photoId = it.id,
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
                                                modifier = modifier.padding(
                                                    end = padding_60,
                                                    bottom = padding_10
                                                ),
                                                textStyle = MaterialTheme.typography.bodyLarge,
                                                onDownloadClick = onDownload
                                            )
                                        },
                                        onLikeClick = onLikeClick,
                                        onRemoveLikeClick = onRemoveLikeClick,
                                        onDownloadClick = onDownloadClick
                                    )
                                }
                            }
                            pagedItems.apply {
                                if (loadState.append is LoadState.Error) {
                                    onLoadError()
                                }
                            }
                            when (uiState.isPagingLoadingError) {
                                true -> {
                                    item {
                                        StaticErrorField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(padding_6)
                                                .testTag(CollectionDetailsTags.PAGING_ERROR_FIELD),
                                            text = stringResource(id = R.string.collections_network_error),
                                            textStyle = MaterialTheme.typography.bodyLargeMedium
                                        )
                                    }
                                }

                                else -> {}
                            }
                        }
                    }
                }
                ClosableErrorField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .alpha(0.9f)
                        .testTag(CollectionDetailsTags.ERROR_FIELD),
                    text = uiState.error,
                    textStyle = MaterialTheme.typography.bodyLargeMedium,
                    onClick = onCloseFieldClick
                )
            }
        }

        is CollectionDetailsContract.State.Success -> {
            Column {
                TitleField(
                    titleText = stringResource(id = R.string.collection_details_title),
                    modifier = Modifier.fillMaxWidth(),
                    onNavigateUp = onNavigateUp
                )
                val pagedItems: LazyPagingItems<PhotoItemModel> =
                    uiState.photoItemModels.collectAsLazyPagingItems()
                LazyColumn(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondary)
                        .testTag(CollectionDetailsTags.LAZY_COLUMN)
                ) {
                    item {
                        CollectionDetailsInfo(collectionDetailsModel = uiState.collectionDetailsModel)
                    }
                    items(
                        pagedItems.itemCount,
                        key = pagedItems.itemKey { collectionItem -> collectionItem.id }) { index ->
                        val item = pagedItems[index]
                        item?.let {
                            PhotoItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onPhotoItemClick(it.id)
                                    },
                                contentScale = ContentScale.FillBounds,
                                photoItemModel = it,
                                userImage = {
                                    UserImageSmall(imageUrl = it.user.userImage)
                                },
                                userInfo = {
                                    UserInfoSmall(
                                        name = it.user.name,
                                        userName = it.user.username
                                    )
                                },
                                likesInfo = { modifier, onLike, onRemoveLike ->
                                    LikesInfoSmall(
                                        modifier = modifier.padding(
                                            end = padding_6,
                                            bottom = padding_10
                                        ),
                                        photoId = it.id,
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
                                        modifier = modifier.padding(
                                            end = padding_60,
                                            bottom = padding_10
                                        ),
                                        textStyle = MaterialTheme.typography.bodyLarge,
                                        onDownloadClick = onDownload
                                    )
                                },
                                onLikeClick = onLikeClick,
                                onRemoveLikeClick = onRemoveLikeClick,
                                onDownloadClick = onDownloadClick
                            )
                        }
                    }
                    pagedItems.apply {
                        if (loadState.append is LoadState.Error) {
                            onLoadError()
                        }
                    }
                    when (uiState.isPagingLoadingError) {
                        true -> {
                            item {
                                ClosableErrorField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(padding_16)
                                        .testTag(CollectionDetailsTags.PAGING_ERROR_FIELD),
                                    text = stringResource(id = R.string.collections_network_error),
                                    textStyle = MaterialTheme.typography.bodyLargeMedium,
                                    onClick = onPagingCloseFieldClick
                                )
                            }
                        }

                        false -> {
                            pagedItems.retry()
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }
}

object CollectionDetailsTags {
    const val LAZY_COLUMN = "CollectionDetailsTags:LAZY_COLUMN"
    const val LOADING_INDICATOR = "CollectionDetailsTags:LOADING_INDICATOR"
    const val ERROR_FIELD = "CollectionDetailsTags:ERROR_FIELD"
    const val PAGING_ERROR_FIELD = "CollectionDetailsTags:PAGING_ERROR_FIELD"
}

@Preview
@Composable
private fun CollectionDetailsScreenPreview() {
    UnsplashTheme(dynamicColor = false) {
        CollectionDetailsScreen(
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
            onNavigateUp = {},
            onNavigateBack = {}
        )
    }
}